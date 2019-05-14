package com.lee.vshare.service.test.fec.sort;

import com.lee.vshare.service.test.fec.ex.Rscode;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 19-3-2
 * @Time 下午5:18
 */
public class FecSortTest {


    static AtomicInteger seqInteger = new AtomicInteger(-1);
    static AtomicInteger groupInteger = new AtomicInteger(-1);
    static AtomicBoolean receiveAble = new AtomicBoolean(true);
    static AtomicInteger count = new AtomicInteger(0);

    static final int CATCH_COUNT = 10;

    static Random random = new Random();

    static BlockingDeque<RTP> rtpDeque = new LinkedBlockingDeque<>();
    static List<RTP> catchList = new ArrayList<>();
    static BlockingDeque<RTP> trackDeque = new LinkedBlockingDeque<>();

    static CatchThread catchThread;

    static int opusLength = 0;
    static BlockingDeque<byte[]> recordDeque = new LinkedBlockingDeque<>();
    static List<byte[]> fecList = new ArrayList<>();
    static BlockingDeque<RTP> sendDeque = new LinkedBlockingDeque<>();
    static final int VALID_COUNT = 5;
    static FecThread fecThread;
    static final int FEC_SIZE = 300;

    static final int RS_COUNT = 4;
    static final int ALL_COUNT = RS_COUNT + VALID_COUNT;
    // all data is 10, checksum data is 3, data stripe length is 1024
    static Rscode rsItem = new Rscode(ALL_COUNT, RS_COUNT, FEC_SIZE);


    public static void main(String[] args) {
        new ReceiveThread().start();

        catchThread = new CatchThread();
        catchThread.start();
//
        new TrackThread().start();


        fecThread = new FecThread();
        fecThread.start();

        new RecordThread().start();
    }

    static class RecordThread extends Thread {
        @Override
        public void run() {
            byte[] opus;
            byte b;
            while (count.addAndGet(1) <= 200) {
                opusLength = random.nextInt(100) + 100;
                opus = new byte[opusLength];
                b = (byte) count.get();
                for (int i = 0; i < opusLength; i++) {
                    opus[i] = b;
                }
                if (opus.length <= FEC_SIZE) { //丢弃长度大于FEC_SIZE的语音包
                    recordDeque.offer(opus);
                }
                System.out.println("RecordThread : opus : " + Arrays.toString(opus));
                if (recordDeque.size() >= VALID_COUNT) {
                    fecThread.unPark();
                }
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class FecThread extends Thread {
        @Override
        public void run() {
            byte[] opus;
            int opusLength;
            int groupNumber;
            while (true) {
                if (recordDeque.size() >= VALID_COUNT) {
                    groupNumber = groupInteger.addAndGet(1);
                    System.out.println("FecThread : recordDeque size = " + recordDeque.size() + "   groupNumber = " + groupNumber);
                    System.out.println("FecThread :  --- fec encode begin --- " + System.currentTimeMillis());
                    for (int i = 0; i < VALID_COUNT; i++) {
                        byte[] opusData = recordDeque.poll();
                        if (null != opusData) {
                            fecList.add(opusData);
                        }
                    }
                    // do fecEncode
                    byte[][] enRs = new byte[ALL_COUNT][FEC_SIZE];
                    for (int i = 0; i < fecList.size(); i++) {
                        opus = fecList.get(i);
                        opusLength = opus.length;
                        for (int j = 0; j < FEC_SIZE; j++) {
                            if (opusLength < FEC_SIZE && j >= opusLength) {
                                enRs[i][j] = 0;
                            } else {
                                enRs[i][j] = opus[j];
                            }
                        }
                    }
                    rsItem.setRs(enRs);
                    rsItem.encoding();
                    System.out.println("FecThread  --- fec encode end --- " + System.currentTimeMillis());

                    byte[][] fecData = new byte[ALL_COUNT][FEC_SIZE];
                    System.arraycopy(rsItem.getRs(), 0, fecData, 0, fecData.length);
                    for (int i = 0; i < fecData.length; i++) {
                        RTP rtp = new RTP(seqInteger.addAndGet(1), System.currentTimeMillis(), fecData[i], 0, groupNumber);
                        System.out.println("FecThread : rtp : " + rtp.toString());
                        sendDeque.offer(rtp);
                    }
                    fecList.clear();
                } else {
                    System.out.println(" FecThread : ------------------ park");
                    LockSupport.park();
                }
            }
        }

        public void unPark() {
            LockSupport.unpark(this);
        }
    }

    static class ReceiveThread extends Thread {
        @Override
        public void run() {
            int oldGroupNumber1 = 0;
            int oldGroupNumber2 = 1;
            List<RTP> list1 = new ArrayList<>(VALID_COUNT);
            List<RTP> list2 = new ArrayList<>(VALID_COUNT);
            RTP rtp;
            int sun = 0;
            while (receiveAble.get()) {
//                RTP rtp = new RTP(atomicInteger.addAndGet(1), System.currentTimeMillis(), null, 0);
//                RTP rtp = new RTP(random.nextInt(100), System.currentTimeMillis(), null, 0);
                try {
                    /** 获取数据 */
                    rtp = sendDeque.takeFirst();
                    if (null != rtp) {
                        sun++;
                        if (sun == 2 || sun == 3 || sun == 6 || sun == 11 || sun == 12 || sun == 17) {
                            continue;
                        }

                        int newGroupNumber = rtp.getGroupNumber();
                        System.out.println("newGroupNumber : " + newGroupNumber);
//                        System.out.println("oldGroupNumber1 : " + oldGroupNumber1 + "   oldGroupNumber2 : " + oldGroupNumber2);
                        if (oldGroupNumber1 == newGroupNumber) { //缓存第一组数据
                            list1.add(rtp);
                            if (list1.size() == VALID_COUNT) {
                                rtpDeque.addAll(fecDecode(list1));
                                list1.clear();
                                if (oldGroupNumber1 > oldGroupNumber2) {
                                    oldGroupNumber1++;
                                } else {
                                    oldGroupNumber1 = oldGroupNumber2 + 1;
                                }
                            }
                        } else if (oldGroupNumber2 == newGroupNumber) { //缓存第二组数据
                            list2.add(rtp);
                            if (list2.size() == VALID_COUNT) {
                                rtpDeque.addAll(fecDecode(list2));
                                list2.clear();
                                if (oldGroupNumber2 > oldGroupNumber1) {
                                    oldGroupNumber2++;
                                } else {
                                    oldGroupNumber2 = oldGroupNumber1 + 1;
                                }
                            }
                        } else if (newGroupNumber > oldGroupNumber1 && newGroupNumber > oldGroupNumber2) { //前两组缓存没达到释放条件时收到另一组  释放数据多的一组
                            if (list1.size() >= list2.size()) {
                                rtpDeque.addAll(fecDecode(list1));
                                list1.clear();
                                if (oldGroupNumber1 > oldGroupNumber2) {
                                    oldGroupNumber1++;
                                } else {
                                    oldGroupNumber1 = oldGroupNumber2 + 1;
                                }
                            } else {
                                rtpDeque.addAll(fecDecode(list2));
                                list2.clear();
                                if (oldGroupNumber2 > oldGroupNumber1) {
                                    oldGroupNumber2++;
                                } else {
                                    oldGroupNumber2 = oldGroupNumber1 + 1;
                                }
                            }
                        } else {
//                            System.out.println("ReceiveThread : -------------------- package that needn't");
                        }
                        if (rtpDeque.size() >= CATCH_COUNT) {
                            catchThread.unPark();
                        }
                    }

                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    static class CatchThread extends Thread {
        @Override
        public void run() {
            while (true) {
                if (rtpDeque.size() >= CATCH_COUNT) {
                    System.out.println("CatchThread : rtpDequeSize : " + rtpDeque.size());
                    System.out.println("CatchThread :  --- sort begin --- " + System.currentTimeMillis());
                    for (int i = 0; i < CATCH_COUNT; i++) {
                        RTP rtp1 = rtpDeque.poll();
                        if (null != rtp1) {
                            catchList.add(rtp1);
                        }
                    }
                    Collections.sort(catchList);
                    System.out.println("CatchThread :  --- sort end --- " + System.currentTimeMillis());
                    trackDeque.addAll(catchList);
                    for (int i = 0; i < catchList.size(); i++) {
                        System.out.println("CatchThread : catchList : " + catchList.get(i).toString());
                    }
                    System.out.println("CatchThread : trackDequeSize : " + trackDeque.size());
                    catchList.clear();
                } else {
                    System.out.println(" CatchThread : park");
                    LockSupport.park();
                }
            }
        }

        public void unPark() {
            LockSupport.unpark(this);
        }
    }

    static class TrackThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    if (trackDeque.size() == 200){
                        for (int i = 0; i < 200; i++) {
                            RTP rtp = trackDeque.take();
                            if (null != rtp) {
                                System.out.println(" trackRunable : " + rtp.toString() + "     " + trackDeque.size());
                            }
                        }
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static List<RTP> fecDecode(List<RTP> rtpList) {
        int number = 0;
        List<RTP> list = new ArrayList<>(VALID_COUNT);
        int[] pack = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
        byte[][] buffer = new byte[9][FEC_SIZE];
        int size = rtpList.size();
//        System.out.println("fecDecode : size : " + size);
        if (size < VALID_COUNT) {
            System.out.println(" --------- invalid for fec --------- ");
            for (int i = 0; i < size; i++) {
                RTP rtp = rtpList.get(i);
                int seqInfo = rtp.getSeq() % ALL_COUNT;
                System.out.println("fecDecode : seqInfo : " + seqInfo);
                if (seqInfo < VALID_COUNT) {
                    list.add(rtp);
                }
            }
            return list;
        }

        for (int i = 0; i < size; i++) {
            RTP rtp = rtpList.get(i);
            int seqInfo = rtp.getSeq() % ALL_COUNT;
            System.out.println("fecDecode : rtp : " + rtp.toString());
            if (seqInfo < VALID_COUNT) {
                list.add(rtp);
            }
            pack[seqInfo] = number;
            buffer[number] = rtp.getData();
            number++;
        }

        System.out.println("pack : " + Arrays.toString(pack));
        for (int i = 0; i < buffer.length; i++) {
            System.out.println("buffer : " + Arrays.toString(buffer[i]));
        }

        if (list.size() < VALID_COUNT) {
            System.out.println(" --------- data had lose, do fec  --------- ");
            rsItem.setRs(new byte[ALL_COUNT][FEC_SIZE]);
            rsItem.decoding(pack, buffer);
            byte[][] rsTemp = rsItem.getRs();
            for (int i = 0; i < VALID_COUNT; i++) {
                byte[] b = rsTemp[i];
                System.out.println("fecDecode : rsTemp : " + Arrays.toString(b));
                for (int j = 0; j < b.length; j++) {
                    if (b[j] > 0) {
                        System.out.println("   i >>>>>>   " + i);
                        RTP rtp = rtpList.get(i);
                        RTP rtp1 = new RTP();
                        rtp1.setSeq(i + (rtp.getGroupNumber() * ALL_COUNT));
                        rtp1.setData(b);
                        list.add(rtp1);
                        break;
                    }
                }
            }
        } else {
            System.out.println(" --------- data is full, don't need fec --------- ");
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println("list : " + list.get(i).toString());
        }
        return list;
    }
}
