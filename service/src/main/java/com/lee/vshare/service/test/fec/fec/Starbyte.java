package com.lee.vshare.service.test.fec.fec;

import static java.lang.System.out;

import java.util.Arrays;

public class Starbyte implements Fec {
    // private int check_data_size;
    private int block_size;
    private int p;
    private int block_nbr;/* block_nbr = p - 1 */
    // int **check_data;
    private byte[][] check_data;
    private int[] restarts;
    private int data_disk_nbr;
    private int allNum;
    private int stripe_unit_size; // stripe size
    private static final int TOLERENCE = 3; // STAR can protect data against 2
    // failures
    private static final int DATA_LENGTH = 1024; // default data length
    private static final int PRIME = 257; // a prime number

    public Starbyte() {

        p = PRIME;
        data_disk_nbr = 4;
        stripe_unit_size = DATA_LENGTH; // 1024

        block_nbr = p - 1; // 256
        block_size = stripe_unit_size / block_nbr; // 1
        allNum = data_disk_nbr + TOLERENCE; // 7

        check_data = new byte[allNum][stripe_unit_size];
        restarts = new int[allNum];
        for (int i = 0; i < allNum; i++) {
            restarts[i] = 0;
        }
    }

    public Starbyte(int disk, int prime, int dataLength) {

        p = prime;
        data_disk_nbr = disk;
        stripe_unit_size = dataLength;

        block_nbr = p - 1;
        block_size = stripe_unit_size / block_nbr;
        allNum = data_disk_nbr + TOLERENCE;

        check_data = new byte[allNum][stripe_unit_size];

        restarts = new int[allNum];
        for (int i = 0; i < allNum; i++) {
            restarts[i] = 0;
        }
    }

    /**
     * an easy test case
     */
    public void setData() {
        for (int i = 0; i < data_disk_nbr; i++) {
            for (int j = 0; j < stripe_unit_size; j++) {
                check_data[i][j] = (byte) ('a' + i);
            }
        }
    }

    // 1 means error, default value is 0
    public void setErrData(int[] err) {
        for (int i = 0; i < allNum; i++) {
            restarts[i] = err[i];
        }
    }

    public String showme() {
        return "STAR";
    }

    /**
     * for testing and debug.
     */
    public void outputData() {

        out.println("The res:");
        for (int i = 0; i < allNum; i++) {
            for (int j = 0; j < stripe_unit_size; j++) {
                out.printf("%c", check_data[i][j]);
            }
        }
    }

    /**
     * for testing and debug.
     */
    public void outputOrigin() {

        out.println("After decoding:");
        for (int i = 0; i < data_disk_nbr; i++) {
            out.printf("data:%d:  ", i);
            for (int j = 0; j < stripe_unit_size; j++) {
                out.printf("%c", check_data[i][j]);
            }
            out.printf("\n");
            // out.println(check_data[i]);
        }
    }

    /**
     * entry function for encoding
     */
    public void encoding() {

        if (stripe_unit_size % block_nbr != 0) {

            throw new RuntimeException(" Cannot  striping. wrong DATA_LENGTH!");
        }

        STAR_encoding_row();
        STAR_encoding_diag1();
        STAR_encoding_diag2();
    }

    /* computing checksum in every row,*check_data[p] */
    private void STAR_encoding_row() {
        int i, j;
        for (i = 0; i < stripe_unit_size; i++) {
            for (j = 0; j < data_disk_nbr; j++) {
                check_data[data_disk_nbr][i] ^= check_data[j][i];
            }

        }
    }

    /* computing checksum in every row,*check_data[p] */
    void STAR_encoding_diag1() {

        int i, j, stripe, k;
        byte[][] tmp;

        tmp = new byte[block_nbr + 1][block_size];

        for (stripe = 0; stripe < block_nbr + 1; stripe++) {
            for (i = 0; i < data_disk_nbr; i++) {
                for (j = 0; j < block_size; j++) {
                    k = (stripe - i + p) % p;
                    if (k < block_nbr) {
                        tmp[stripe][j] ^= check_data[i][(stripe - i + p) % p
                                                        * block_size + j];

                    }
                }

            }

        }

        /* after store diagonal line checksum in tmp[block_nbr] */
        /* we need using diagonal line checksum and s to do xor compute */
        for (i = 0; i < block_nbr; i++) {
            for (j = 0; j < block_size; j++) {
                tmp[i][j] = (byte) (tmp[i][j] ^ tmp[block_nbr][j]);

            }
        }

        for (i = 0; i < block_nbr; i++) {
            // memmove(check_data[data_disk_nbr + 1] + (i * block_size), tmp[i],
            // block_size * sizeof(int));
            // memmove(check_data[data_disk_nbr + 1] + (i * block_size), tmp[i],
            // block_size);
            System.arraycopy(tmp[i], 0, check_data[data_disk_nbr + 1], i
                             * block_size, block_size);
        }
    }

    /* diagonal line checksum, slope -1,*check_data[p+2] */
    void STAR_encoding_diag2() {
        int i, j, stripe, k;
        byte[] tmp;

        // tmp = (int*)malloc(sizeof(int) * (p * block_size));
        tmp = new byte[p * block_size];
        // memset(tmp, 0, p*block_size*sizeof(int));
        // memset(tmp, 0, p*block_size*sizeof(char));

        for (stripe = 0; stripe < block_nbr + 1; stripe++) {
            for (i = 0; i < data_disk_nbr; i++) {
                for (j = 0; j < block_size; j++) {
                    k = (stripe + i + p) % p;
                    if (k < block_nbr)
                        tmp[stripe * block_size + j] ^= check_data[i][k
                                                        * block_size + j];
                }
            }
        }
        for (i = 0; i < block_nbr; i++) {
            for (j = 0; j < block_size; j++) {
                tmp[i * block_size + j] ^= tmp[block_nbr * block_size + j];
            }
        }

        // memmove( check_data[data_disk_nbr + 2], tmp, check_data_size *
        // sizeof(int));
        // memmove( check_data[data_disk_nbr + 2], tmp, stripe_unit_size *
        // sizeof(char));
        System.arraycopy(tmp, 0, check_data[data_disk_nbr + 2], 0,
                         stripe_unit_size);

    }

    public void decoding() {
        int i, j, k, m, stripe;
        int rs_nbr = 0; /* �ܹ���rs_nbr���� */
        int rs_data_nbr = 0; /* �����������rs_data_nbr���������� */
        int rs_check_nbr = 0;/* �����������rs_check_nbr����У���� */
        int rs_disk1 = -1;
        int rs_disk2 = -1;
        int rs_disk3 = -1;

        // if( check_data_size % block_nbr != 0)
        if (stripe_unit_size % block_nbr != 0) {
            throw new RuntimeException(" Cannot  striping. wrong DATA_LENGTH!");
        }

        for (i = 0; i < data_disk_nbr + 2; i++) {
            if (restarts[i] == 1) {
                rs_disk1 = i;
                break;
            }
        }

        if (rs_disk1 != -1) {
            for (i = rs_disk1 + 1; i < data_disk_nbr + 2; i++) {
                if (restarts[i] == 1) {
                    rs_disk2 = i;
                    break;
                }
            }
        }
        if (rs_disk2 != -1) {
            for (i = rs_disk2 + 1; i < data_disk_nbr + 2; i++) {
                if (restarts[i] == 1) {
                    rs_disk3 = i;
                    break;
                }
            }
        }

        // if(rs_disk1 != -1)memset(check_data[rs_disk1], 0
        // ,stripe_unit_size*sizeof(char));
        // if(rs_disk2 != -1)memset(check_data[rs_disk2], 0
        // ,stripe_unit_size*sizeof(char));
        // if(rs_disk3 != -1)memset(check_data[rs_disk3], 0
        // ,stripe_unit_size*sizeof(char));
        if (rs_disk1 != -1) {
            Arrays.fill(check_data[rs_disk1], (byte) 0);
        }
        if (rs_disk2 != -1) {
            Arrays.fill(check_data[rs_disk2], (byte) 0);
        }
        if (rs_disk3 != -1) {
            Arrays.fill(check_data[rs_disk3], (byte) 0);
        }

        // out.printf("rs_disks : %d %d %d\n", rs_disk1,rs_disk2,rs_disk3);

        for (i = 0; i <= data_disk_nbr + 2; i++) {
            rs_nbr += restarts[i];
        }

        if (TOLERENCE < rs_nbr) {
            throw new RuntimeException(" Too many error data!");
        }
        /* printf("rs_nbr = %d\n",rs_nbr); */

        for (i = 0; i < data_disk_nbr; i++) {
            rs_data_nbr += restarts[i];
        }

        rs_check_nbr = rs_nbr - rs_data_nbr;

        if (rs_data_nbr == 0) {
            if (restarts[data_disk_nbr] == 1)
                STAR_encoding_row();
            if (restarts[data_disk_nbr + 1] == 1)
                STAR_encoding_diag1();
            if (restarts[data_disk_nbr + 2] == 1)
                STAR_encoding_diag2();
        }

        if (rs_data_nbr == 1) {
            if (rs_check_nbr <= 1) {
                Evenodd_decoding(restarts);
                if (restarts[data_disk_nbr + 2] == 1)
                    STAR_encoding_diag2();
            }

            if (rs_check_nbr == 2) {
                if (restarts[data_disk_nbr] == 0) { /* rowУ����û���� */
                    // for( i = 0; i < check_data_size; i++)
                    for (i = 0; i < stripe_unit_size; i++) {
                        for (j = 0; j <= data_disk_nbr; j++) {
                            if (j != rs_disk1)
                                check_data[rs_disk1][i] ^= check_data[j][i];
                        }
                    }
                    STAR_encoding_diag1();
                    STAR_encoding_diag2();
                }
                if (restarts[data_disk_nbr] == 1) { /* rowУ���̳��� */
                    if (restarts[data_disk_nbr + 2] == 1) {
                        Evenodd_decoding(restarts);
                        STAR_encoding_diag2();
                    }
                    if (restarts[data_disk_nbr + 1] == 1) {
                        Evenodd_decoding_1(rs_disk1, rs_disk2);
                        STAR_encoding_diag1();
                    }
                }
            }
        }

        if (rs_data_nbr == 2) {
            if (rs_check_nbr == 0) {
                Evenodd_decoding(restarts);
            } else /* rs_check_nbr == 1 */
                if (restarts[data_disk_nbr] == 1) {
                    /********************************************************************************************
                     ********************************************************************************************
                     ********************************************************************************************
                     ********************************************************************************************
                     ********************************************************************************************
                     ********************************************************************************************
                     ********************************************************************************************/
                    // int *tmp_for_s1s2;
                    byte[] tmp_for_s1s2;
                    // tmp_for_s1s2 = (int *)malloc(sizeof(int) * block_size);
                    tmp_for_s1s2 = new byte[block_size];

                    for (i = 0; i < block_nbr; i++) { /* ���s1 xor s2��ֵ */
                        for (j = 0; j < block_size; j++) {
                            tmp_for_s1s2[j] ^= check_data[data_disk_nbr + 1][i
                                               * block_size + j];
                            tmp_for_s1s2[j] ^= check_data[data_disk_nbr + 2][i
                                               * block_size + j];
                        }
                    }

                    // int **tmp;/*��Ÿ���s~~*/
                    byte[][] tmp;/* ��Ÿ���s~~ */
                    // tmp = (int **)malloc( sizeof(int *) * 3);
                    tmp = new byte[3][p * block_size];

                    // for( i = 0; i < check_data_size; i++)
                    for (i = 0; i < stripe_unit_size; i++) {
                        for (j = 0; j <= data_disk_nbr; j++) {
                            tmp[0][i] ^= check_data[j][i];
                        }
                    }
                    for (stripe = 0; stripe < block_nbr + 1; stripe++) {
                        for (i = 0; i < data_disk_nbr; i++) {
                            for (j = 0; j < block_size; j++) {
                                k = (stripe - i + p) % p;
                                if (k < block_nbr) {
                                    tmp[1][stripe * block_size + j] ^= check_data[i][k
                                                                       * block_size + j];
                                }
                            }
                        }
                    }
                    // for( i = 0; i < check_data_size; i++)
                    for (i = 0; i < stripe_unit_size; i++) {
                        tmp[1][i] ^= check_data[data_disk_nbr + 1][i];
                    }

                    for (stripe = 0; stripe < block_nbr + 1; stripe++) {
                        for (i = 0; i < data_disk_nbr; i++) {
                            for (j = 0; j < block_size; j++) {
                                k = (stripe + i + p) % p;
                                if (k < block_nbr) {
                                    tmp[2][stripe * block_size + j] ^= check_data[i][k
                                                                       * block_size + j];
                                }
                            }
                        }
                    }
                    // for( i = 0 ;i < check_data_size; i++)
                    for (i = 0; i < stripe_unit_size; i++) {
                        tmp[2][i] ^= check_data[data_disk_nbr + 2][i];
                    }
                    /* ����,����s~~�Ѿ����,�����**tmp�� */

                    // int **tmp_for_xor;
                    byte[][] tmp_for_xor;
                    // tmp_for_xor = (int **)malloc( sizeof(int *) * p);
                    tmp_for_xor = new byte[p][block_size];

                    for (i = 0; i < block_nbr + 1; i++) {
                        for (j = 0; j < block_size; j++) {
                            tmp_for_xor[i][j] = (byte) (tmp[0][i * block_size + j]
                                                        ^ tmp[0][((rs_disk2 - rs_disk1 + i) % p)
                                                                 * block_size + j]
                                                        ^ tmp[1][((rs_disk2 + p + i) % p) * block_size
                                                                 + j]
                                                        ^ tmp[2][((p - rs_disk1 + i) % p) * block_size
                                                                 + j] ^ tmp_for_s1s2[j]);
                        }
                    }

                    /*
                     * *tmp[0] = c[p][0] xor c[s][rs_disk2 - rs_disk1]tmp[i] =
                     * c[p][i] xor c[s][(rs_disk2 - rs_disk1 + i)%p]
                     * ...................................
                     */
                    k = p - 1 - (rs_disk2 - rs_disk1);/*
                                                 * c[p][k] xor c[p][p-1] =
                                                 * tmp_for_xor
                                                 * [k],����c[rs_disk2][p-1]��ֵȫΪ��
                                                 */
                    for (i = 0; i < block_size; i++) {
                        check_data[data_disk_nbr][k * block_size + i] = tmp_for_xor[k][i];
                    }

                    m = block_nbr - 1;
                    while (0 != m) {
                        i = (k + rs_disk1 - rs_disk2 + p) % p;

                        for (j = 0; j < block_size; j++) {
                            check_data[data_disk_nbr][i * block_size + j] = (byte) (tmp_for_xor[i][j] ^ check_data[data_disk_nbr][k
                                    * block_size + j]);
                        }
                        k = i;
                        m--;
                    }
                    restarts[data_disk_nbr] = 0;/* ��p���ѻָ� */

                    Evenodd_decoding(restarts);
                }

            if (restarts[data_disk_nbr + 1] == 1) {
                Evenodd_decoding_1(rs_disk1, rs_disk2);
                STAR_encoding_diag1();
            }
            if (restarts[data_disk_nbr + 2] == 1) {
                Evenodd_decoding(restarts);
                STAR_encoding_diag2();
            }
        }

        if (rs_data_nbr == 3) { /* ��ͷϷ--���������̴����� */
            int r, s, t, u, v;

            // int **tmp;/*��Ÿ���s~~*/
            byte[][] tmp;/* ��Ÿ���s~~ */
            // tmp = (int **)malloc( sizeof(int *) * 3);
            tmp = new byte[3][p * block_size];

            // int *tmp_for_s1;
            byte[] tmp_for_s1;
            // tmp_for_s1 = (int *)malloc(sizeof(int) * block_size);
            // memset( tmp_for_s1, 0, sizeof(int) * block_size);
            tmp_for_s1 = new byte[block_size];

            // int *tmp_for_s2;
            byte[] tmp_for_s2;
            // tmp_for_s2 = (int *)malloc(sizeof(int) * block_size);
            // memset( tmp_for_s2, 0, sizeof(int) * block_size);
            tmp_for_s2 = new byte[block_size];

            // int **tmp_for_xor;
            byte[][] tmp_for_xor;
            // tmp_for_xor = (int **)malloc( sizeof(int *) * p);
            tmp_for_xor = new byte[p][block_size];

            /* ���������s~~ */
            for (i = 0; i < block_nbr; i++) {
                for (j = 0; j < block_size; j++) {
                    tmp_for_s1[j] ^= check_data[data_disk_nbr][i * block_size
                                     + j];
                    tmp_for_s1[j] ^= check_data[data_disk_nbr + 1][i
                                     * block_size + j];

                    tmp_for_s2[j] ^= check_data[data_disk_nbr][i * block_size
                                     + j];
                    tmp_for_s2[j] ^= check_data[data_disk_nbr + 2][i
                                     * block_size + j];
                }
            }

            // for( i = 0; i < check_data_size; i++)
            for (i = 0; i < stripe_unit_size; i++) {
                for (j = 0; j <= data_disk_nbr; j++) {
                    tmp[0][i] ^= check_data[j][i];
                }
            }

            for (stripe = 0; stripe < block_nbr + 1; stripe++) {
                for (i = 0; i < data_disk_nbr; i++) {
                    for (j = 0; j < block_size; j++) {
                        k = (stripe - i + p) % p;
                        if (k < block_nbr) {
                            tmp[1][stripe * block_size + j] ^= check_data[i][k
                                                               * block_size + j];
                        }
                    }
                }
            }
            for (i = 0; i < block_nbr + 1; i++) {
                for (j = 0; j < block_size; j++) {
                    if (i < block_nbr)
                        tmp[1][i * block_size + j] ^= (check_data[data_disk_nbr + 1][i
                                                       * block_size + j] ^ tmp_for_s1[j]);
                    else
                        tmp[1][i * block_size + j] ^= tmp_for_s1[j];
                }
            }

            for (stripe = 0; stripe < block_nbr + 1; stripe++) {
                for (i = 0; i < data_disk_nbr; i++) {
                    for (j = 0; j < block_size; j++) {
                        k = (stripe + i + p) % p;
                        if (k < block_nbr) {
                            tmp[2][stripe * block_size + j] ^= check_data[i][k
                                                               * block_size + j];
                        }
                    }
                }
            }

            for (i = 0; i < block_nbr + 1; i++) {
                for (j = 0; j < block_size; j++) {
                    if (i < block_nbr)
                        tmp[2][i * block_size + j] ^= (check_data[data_disk_nbr + 2][i
                                                       * block_size + j] ^ tmp_for_s2[j]);
                    else
                        tmp[2][i * block_size + j] ^= tmp_for_s2[j];
                }
            }
            /* ����,����s~~�Ѿ����,�����**tmp�� */

            r = rs_disk1;
            s = rs_disk2;
            t = rs_disk3;
            u = s - r;
            v = t - s;

            if (u == v) { /* ������������������̶ԳƵ���� */
                for (i = 0; i < block_nbr + 1; i++) {
                    for (j = 0; j < block_size; j++) {
                        tmp_for_xor[i][j] = (byte) (tmp[0][i * block_size + j]
                                                    ^ tmp[0][((t - r + i) % p) * block_size + j]
                                                    ^ tmp[1][((t + p + i) % p) * block_size + j] ^ tmp[2][((p
                                                            - r + i) % p)
                                                            * block_size + j]);
                    }
                }
                /*
                 * *tmp_for_xor[0] = c[s][0] xor c[s][t-r]tmp_for_xor[1] =
                 * c[s][1] xor c[s][(t-r+1)%p]tmp_for_xor[i] = c[s][i] xor
                 * c[s][(t-r+i)%p] ...................................
                 */
                k = p - 1 - (t - r);/*
                                     * c[s][k] xor c[s][p-1] =
                                     * tmp_for_xor[k],����c[s][p-1]��ֵȫΪ��
                                     */
                for (i = 0; i < block_size; i++) {
                    check_data[s][k * block_size + i] = tmp_for_xor[k][i];
                }

                m = block_nbr - 1;
                while (0 != m) {
                    i = (r - t + k + p) % p;

                    for (j = 0; j < block_size; j++) {
                        check_data[s][i * block_size + j] = (byte) (tmp_for_xor[i][j] ^ check_data[s][k
                                                            * block_size + j]);
                    }
                    k = i;
                    m--;
                }
            }

            else if (u != v) { /* �����ԳƵ���� */
                int d;/* d��cross */
                // int **flag;
                byte[][] flag;

                // flag = (int**)malloc(3*sizeof(int*));
                flag = new byte[3][p];

                for (d = 0; d <= p; d++) {
                    if ((u + v * d) % p == 0)
                        break;
                }

                for (i = 0; i < d; i++) {
                    /* б��Ϊ-1�ĶԽ��� */
                    flag[0][(0 + i * v) % p]++;
                    flag[1][(s - r + i * v) % p]++;
                    flag[2][(t - r + i * v) % p]++;

                    /* б��Ϊ+1�ĶԽ��� */
                    flag[2][(0 + i * v) % p]++;
                    flag[1][(t - s + i * v) % p]++;
                    flag[0][(t - r + i * v) % p]++;

                    /**/

                }
                /* ɨ��flag[3][p-1],0��2��ֱ�Ӻ��� ,��1�ĸ����ֲ� */
                /* ��������֪��,��һ���ҳ��Ŀ϶���a[s][u] XOR a[s][p-u]�Ľ�� */
                int[] count;
                // byte[] count;
                // count = (int*)malloc(sizeof(int) * p);
                // memset(count, 0, sizeof(int)*p);
                count = new int[p];

                for (i = 0; i < p; i++) { /* ����i���м���1 */
                    for (j = 0; j < 3; j++) {
                        if (flag[j][i] == 1)
                            count[i]++;
                    }
                }

                for (m = 0; m < block_nbr + 1; m++) { /* ���a xor a */
                    for (i = 0; i < p; i++) {
                        if (count[i] == 2 || count[i] == 3) {
                            for (j = 0; j < block_size; j++)
                                tmp_for_xor[m /** v */
                                           ][j] ^= tmp[0][((i + m /** v */
                                                           ) % p) * block_size + j];
                        }
                    }
                    for (i = 0; i < d; i++) {
                        for (j = 0; j < block_size; j++) {
                            tmp_for_xor[m][j] ^= tmp[1][((t + p + 0 + i * v + m) % p)
                                                        * block_size + j];
                            tmp_for_xor[m][j] ^= tmp[2][((p - r + 0 + i * v + m) % p)
                                                        * block_size + j];
                        }
                    }
                }
                /*
                 * ����,*tmp_for_xor[0] = c[s][u] xor c[s][p-u]tmp_for_xor[i] =
                 * c[s][(u + i)%p] xor c[s][ ( p-u+i)%p ]
                 * ...............................................
                 */
                i = u - 1;
                k = (u + i) % p;/*
                                 * c[s][k] xor c[s][p-1] =
                                 * tmp_for_xor[k],����c[s][p-1]��ֵȫΪ��
                                 */
                for (j = 0; j < block_size; j++) {
                    check_data[s][k * block_size + j] = tmp_for_xor[i][j];
                }

                m = block_nbr - 1;
                while (0 != m) {
                    i = (k + u) % p;

                    for (j = 0; j < block_size; j++) {
                        check_data[s][((u + i) % p) * block_size + j] = (byte) (tmp_for_xor[i][j] ^ check_data[s][k
                                * block_size + j]);
                    }
                    k = (u + i) % p;
                    m--;
                }
                /* ����s,���count[i]==3,��s = s XOR s[0][i],����, һ��������������Ͷ��ҳ����� */
                /* �ٿ�count[u]��count[p-u]�ǵ���1���ǵ���2 */
                /*
                 * if((count[u] == 1) && (count[p-u]==1)) { 则a[s][u] XOR
                 * a[s][p-u] = s XOR s[1][t] XOR s[2][(p-r)%p] XOR s[1][(t+v)%p]
                 * XOR ...... } else if(count[u] ==2) { 则s[0][u]和s[0][p-u]都得算上
                 * }
                 */
            }

            restarts[s] = 0;/* s盘已恢复 */

            Evenodd_decoding(restarts);
        }
    }

    void Evenodd_decoding_1(int rs_disk1, int rs_disk2)/*
                                                         * У������:��У������б��Ϊ��1�ĶԽ���У��;
                                                         * ��evenodd
                                                         */
    {
        /*
        * ����ֻ�����������:1,һ�������̴�+��У���̴� 2,���������̴�
        */

        int i, j, stripe, k;
        // int *tmp;
        byte[] tmp;
        // tmp = (int*)malloc(sizeof(int) * (p * block_size));
        // memset(tmp, 0, p*block_size*sizeof(int));
        tmp = new byte[p * block_size];

        // int *tmp_for_s;
        byte[] tmp_for_s;
        // tmp_for_s = (int *)malloc(sizeof(int) * block_size);
        // memset(tmp_for_s, 0, block_size *sizeof(int));
        tmp_for_s = new byte[block_size];

        if (rs_disk1 < data_disk_nbr && rs_disk2 == data_disk_nbr) { /* һ�������̴�+��У���̴� */
            for (stripe = 0; stripe < block_nbr + 1; stripe++) {
                for (i = 0; i < data_disk_nbr; i++) {
                    for (j = 0; j < block_size; j++) {
                        k = (stripe + i + p) % p;
                        if (k < block_nbr)
                            tmp[stripe * block_size + j] ^= check_data[i][k
                                                            * block_size + j];
                    }
                }
            }
            /* �����ƵĶԽ���У��������tmp��,s��û�д��� */
            /* �ҳ�s */
            stripe = (p - rs_disk1 - 1) % p;/* rs_disk1û�в����stripe�����Ƶ�У��s */

            for (i = 0; i < block_size; i++) {
                if (stripe == p - 1)
                    tmp_for_s[i] = tmp[stripe * block_size + i];
                else
                    tmp_for_s[i] = (byte) (tmp[stripe * block_size + i] ^ check_data[data_disk_nbr + 2][stripe
                                           * block_size + i]);
            }

            for (i = 0; i < block_nbr; i++) {
                for (j = 0; j < block_size; j++) {
                    tmp[i * block_size + j] ^= (tmp_for_s[j] ^ check_data[data_disk_nbr + 2][i
                                                * block_size + j]);
                }
            }
            for (i = 0; i < block_size; i++)
                tmp[block_nbr * block_size + i] ^= tmp_for_s[i];
            /* ����,�ûָ������ݶ��ѷ���tmp����. */

            for (i = 0; i < p; i++) {
                j = (i + p + rs_disk1) % p;
                if (j < p - 1) {
                    // memmove( check_data[rs_disk1] + j * block_size, tmp + i *
                    // block_size, block_size *
                    // sizeof(int));/*********************/
                    // memmove( check_data[rs_disk1] + j * block_size, tmp + i *
                    // block_size, block_size * sizeof(char));
                    System.arraycopy(tmp, i * block_size, check_data[rs_disk1],
                                     j * block_size, block_size);
                }
            }
            STAR_encoding_row();
        }

        if (rs_disk1 < data_disk_nbr && rs_disk2 < data_disk_nbr) {
            for (i = 0; i < block_nbr; i++) {
                for (j = 0; j < block_size; j++) {
                    tmp_for_s[j] ^= check_data[data_disk_nbr][i * block_size
                                    + j];
                    tmp_for_s[j] ^= check_data[data_disk_nbr + 2][i
                                    * block_size + j];
                }
            }

            /*
             * for( i = 0; i < block_nbr; i++) { for( j = 0; j < block_size;
             * j++) { check_data[p + 1][i * block_size + j] ^= tmp_for_s[j]; }
             * }���϶����ڴ���s
             */

            for (stripe = 0; stripe < block_nbr + 1; stripe++) {
                for (i = 0; i < data_disk_nbr; i++) {
                    for (j = 0; j < block_size; j++) {
                        k = (stripe + i + p) % p;
                        if (k < block_nbr)
                            tmp[stripe * block_size + j] ^= check_data[i][k
                                                            * block_size + j];
                    }
                }
            }
            for (i = 0; i < block_nbr; i++) {
                for (j = 0; j < block_size; j++) {
                    if (i < block_nbr)
                        tmp[i * block_size + j] ^= (check_data[data_disk_nbr + 2][i
                                                    * block_size + j] ^ tmp_for_s[j]);
                    // else tmp[ i * block_size + j] ^= tmp_for_s[j];
                }
            }
            for (j = 0; j < block_size; j++)
                tmp[block_nbr * block_size + j] ^= tmp_for_s[j];
            /* �����Ƶ�s~ ����tmp�� */

            stripe = (p - rs_disk1 - 1) % p;/* rs_disk1δ�����stripe�����ƵĶԽ���У�� */

            // memmove( tmp_for_s, tmp + stripe * block_size, block_size*
            // sizeof(int));/*���ں����ò���s��,����*tmp_for_s����������Ÿ����м�ֵ*/
            // memmove( tmp_for_s, tmp + stripe * block_size, block_size*
            // sizeof(char));
            System.arraycopy(tmp, stripe * block_size, tmp_for_s, 0, block_size);

            while (true) {
                k = (stripe + rs_disk2 + p) % p;/* ��rs_disk2�������̵ĵ�k���ڵ�stripe������ */
                if (k == block_nbr)
                    break;
                // memmove( check_data[rs_disk2] + k * block_size, tmp_for_s,
                // block_size * sizeof(int));
                // memmove( check_data[rs_disk2] + k * block_size, tmp_for_s,
                // block_size * sizeof(char));
                System.arraycopy(tmp_for_s, 0, check_data[rs_disk2], k
                                 * block_size, block_size);
                /* ��ͨ����k�����У�����ָ�rs_disk1�еĵ�k�� */
                for (i = 0; i < block_size; i++) {
                    for (j = 0; j <= data_disk_nbr; j++)
                        if (j != rs_disk1)
                            check_data[rs_disk1][k * block_size + i] ^= check_data[j][k
                                    * block_size + i];
                }

                stripe = (k - rs_disk1 + p) % p;/* rs_disk1�̵ĵ�k���ڵ�stripe������ */

                for (i = 0; i < block_size; i++) {
                    tmp_for_s[i] = (byte) (check_data[rs_disk1][k * block_size
                                           + i] ^ tmp[stripe * block_size + i]);
                }
            }
        }
    }

    void Evenodd_decoding(int[] restarts) { /* �˺��������ָ�����EVENODDԭ�����1-2������ */
        int i, j, stripe, k;
        int rs_disk1 = -1;
        int rs_disk2 = -1;
        int rs_nbr = 0;

        for (i = 0; i < data_disk_nbr + 2; i++) {
            if (restarts[i] == 1) {
                rs_disk1 = i;
                rs_nbr++;
                break;
            }
        }
        if (rs_disk1 != -1) {
            for (i = rs_disk1 + 1; i < data_disk_nbr + 2; i++) {
                if (restarts[i] == 1) {
                    rs_disk2 = i;
                    rs_nbr++;
                    break;
                }
            }
        }
        // if(rs_disk1 != -1)memset(check_data[rs_disk1], 0 ,check_data_size *
        // sizeof(int));
        // if(rs_disk2 != -1)memset(check_data[rs_disk2], 0 ,check_data_size *
        // sizeof(int));
        if (rs_disk1 != -1) {
            Arrays.fill(check_data[rs_disk1], (byte) 0);
        }
        if (rs_disk2 != -1) {
            Arrays.fill(check_data[rs_disk2], (byte) 0);
        }

        if (rs_disk1 >= data_disk_nbr) { /* �������:������û�г��� */
            if (restarts[data_disk_nbr] == 1) { /* ��У�� */
                STAR_encoding_row();
            }
            if (restarts[data_disk_nbr + 1] == 1) { /* б��Ϊ��1�ĶԽ���У�� */
                STAR_encoding_diag1();
            }
        }

        if (rs_disk1 < data_disk_nbr && rs_nbr == 1) { /* ֻ��һ�������̳������� */
            // for( i = 0; i < check_data_size; i++)
            for (i = 0; i < stripe_unit_size; i++) {
                for (j = 0; j <= data_disk_nbr; j++) {
                    if (j != rs_disk1) {
                        check_data[rs_disk1][i] ^= check_data[j][i];
                    }
                }
            }
        }

        if (rs_nbr == 2 && rs_disk1 < data_disk_nbr
            && rs_disk2 >= data_disk_nbr) { /* һ�������̴�+һ��У���̴����� */
            if (rs_disk2 == data_disk_nbr + 1) { /* �����У������б��Ϊ1�ĶԽ���У�� */
                // for(i = 0; i < check_data_size; i++)/*������У��ָ�������*/
                for (i = 0; i < stripe_unit_size; i++)
                    /* ������У��ָ������� */
                    for (j = 0; j <= data_disk_nbr; j++)
                        if (j != rs_disk1)
                            check_data[rs_disk1][i] ^= check_data[j][i];
                STAR_encoding_diag1();
            }

            if (rs_disk2 == data_disk_nbr) {
                /* int i,j,stripe,k; */
                // int *tmp;
                byte[] tmp;
                // tmp = (int*)malloc(sizeof(int) * (p * block_size));
                // memset(tmp, 0, p * block_size * sizeof(int));
                tmp = new byte[p * block_size];

                // int *tmp_for_s;
                byte[] tmp_for_s;
                // tmp_for_s = (int *)malloc(sizeof(int) * block_size);
                // memset(tmp_for_s, 0, block_size *sizeof(int));
                tmp_for_s = new byte[block_size];

                for (stripe = 0; stripe < block_nbr + 1; stripe++) {
                    for (i = 0; i < data_disk_nbr; i++) {
                        for (j = 0; j < block_size; j++) {
                            k = (stripe - i + p) % p;
                            if (k < block_nbr)
                                tmp[stripe * block_size + j] ^= check_data[i][k
                                                                * block_size + j];
                        }
                    }
                }

                /* �����ƵĶԽ���У��������tmp��,s��û�д��� */
                /* �ҳ�s */
                stripe = (rs_disk1 + p - 1) % p;/* rs_disk1û�в����stripe�����Ƶ�У��s */
                for (i = 0; i < block_size; i++) {
                    if (stripe == p - 1)
                        tmp_for_s[i] = tmp[stripe * block_size + i];
                    else
                        tmp_for_s[i] = (byte) (tmp[stripe * block_size + i] ^ check_data[data_disk_nbr + 1][stripe
                                               * block_size + i]);
                }

                for (i = 0; i < block_nbr; i++) {
                    for (j = 0; j < block_size; j++) {
                        tmp[i * block_size + j] ^= (tmp_for_s[j] ^ check_data[data_disk_nbr + 1][i
                                                    * block_size + j]);
                    }
                }
                for (j = 0; j < block_size; j++)
                    tmp[block_nbr * block_size + j] ^= tmp_for_s[j];
                /* ����,�ûָ������ݶ��ѷ���tmp����. */

                for (i = 0; i < p; i++) {
                    j = (i + p - rs_disk1) % p;
                    if (j < p - 1) {
                        // memmove( check_data[rs_disk1] + j * block_size, tmp +
                        // i * block_size, block_size *
                        // sizeof(int));/*********************/
                        // memmove( check_data[rs_disk1] + j * block_size, tmp +
                        // i * block_size, block_size *
                        // sizeof(char));/*********************/
                        System.arraycopy(tmp, i * block_size,
                                         check_data[rs_disk1], j * block_size,
                                         block_size);
                        /*********************/
                    }
                }

                STAR_encoding_row();
            }
        }

        if (rs_nbr == 2 && rs_disk1 < data_disk_nbr && rs_disk2 < data_disk_nbr) { /* ���������̳��� */

            // int *tmp;
            byte[] tmp;
            // tmp = (int*)malloc(sizeof(int) * (p * block_size));
            // memset(tmp, 0, p*block_size*sizeof(int));
            tmp = new byte[p * block_size];

            /* ��ͨ����У����Խ���У�����s */
            // int *tmp_for_s;
            byte[] tmp_for_s;
            // tmp_for_s = (int *)malloc(sizeof(int) * block_size);
            // memset(tmp_for_s, 0, block_size *sizeof(int));
            tmp_for_s = new byte[block_size];

            for (i = 0; i < block_nbr; i++) {
                for (j = 0; j < block_size; j++) {
                    tmp_for_s[j] ^= check_data[data_disk_nbr][i * block_size
                                    + j];
                    tmp_for_s[j] ^= check_data[data_disk_nbr + 1][i
                                    * block_size + j];
                }
            }

            for (stripe = 0; stripe < block_nbr + 1; stripe++) {
                for (i = 0; i < data_disk_nbr; i++) {
                    k = (stripe - i + p) % p;
                    if (k < block_nbr)
                        for (j = 0; j < block_size; j++) {
                            tmp[stripe * block_size + j] ^= check_data[i][k
                                                            * block_size + j];
                        }
                }
            }
            for (i = 0; i < block_nbr; i++) {
                for (j = 0; j < block_size; j++) {
                    tmp[i * block_size + j] ^= (check_data[data_disk_nbr + 1][i
                                                * block_size + j] ^ tmp_for_s[j]);
                }
            }
            for (j = 0; j < block_size; j++)
                tmp[block_nbr * block_size + j] ^= tmp_for_s[j];
            /* �����Ƶ�s~����tmp�� */

            stripe = (rs_disk1 + p - 1) % p;/* rs_disk1δ�����stripe�����ƵĶԽ���У�� */
            // memmove( tmp_for_s, tmp + stripe * block_size, block_size *
            // sizeof(int));/*���ں����ò���s��,����*tmp_for_s����������Ÿ����м�ֵ*/
            // memmove( tmp_for_s, tmp + stripe * block_size, block_size *
            // sizeof(char));/*���ں����ò���s��,����*tmp_for_s����������Ÿ����м�ֵ*/
            System.arraycopy(tmp, stripe * block_size, tmp_for_s, 0, block_size);
            while (true) {
                k = (stripe - rs_disk2 + p) % p;/* ��rs_disk2�������̵ĵ�k���ڵ�stripe������ */
                if (k == block_nbr)
                    break;
                // memmove( check_data[rs_disk2] + k * block_size, tmp_for_s,
                // block_size * sizeof(int));
                // memmove( check_data[rs_disk2] + k * block_size, tmp_for_s,
                // block_size * sizeof(char));
                System.arraycopy(tmp_for_s, 0, check_data[rs_disk2], k
                                 * block_size, block_size);

                /* ��ͨ����k�����У�����ָ�rs_disk1�еĵ�k�� */
                for (j = 0; j < block_size; j++) {
                    for (i = 0; i <= data_disk_nbr; i++)
                        if (i != rs_disk1)
                            check_data[rs_disk1][k * block_size + j] ^= check_data[i][k
                                    * block_size + j];
                }

                stripe = (k + rs_disk1 + p) % p;/* rs_disk1�̵ĵ�k���ڵ�stripe������ */

                for (j = 0; j < block_size; j++) {
                    tmp_for_s[j] = (byte) (check_data[rs_disk1][k * block_size
                                           + j] ^ tmp[stripe * block_size + j]);
                }

            }
        }

    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        out.printf("starting");

        final int NUM = 7;
        int[] err = new int[NUM];
        Starbyte starItem = new Starbyte();
        // star starItem = new star(6,257,1024);

        starItem.setData();
        starItem.encoding();
        starItem.outputData();

        // 1 means fault data
        for (int i = 0; i < NUM; i++) {
            err[i] = 0;
        }

        err[0] = 1;
        // err[1]=1;
        // err[2]=1;
        err[3] = 1;
        err[6] = 1;

        starItem.setErrData(err);
        starItem.decoding();
        starItem.outputOrigin();

    }

}
