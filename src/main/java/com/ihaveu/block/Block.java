package com.ihaveu.block;

import com.ihaveu.util.StringUtil;
import com.ihaveu.wallet.Transaction;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ZBK on 2018-03-31.
 */
public class Block {

    /**
     * 当前区块的hash
     */
    public String hash;

    /**
     * 前一个区块的hash
     */
    public String previousHash;

//    /**
//     * 当前区块的数据
//     */
//    private String data;

    /**
     * 时间戳
     */
    private long timeStamp;

    private int nonce;
    public String merkleRoot;


    public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message.

    public Block( String previousHash) {
        this.previousHash = previousHash;
//        this.data = data;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        merkleRoot
                        );
        return calculatedhash;
    }

    public void mineBlock(int difficulty) {
        merkleRoot = StringUtil.getMerkleRoot(transactions);
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }
    //Add transactions to this block
    public boolean addTransaction(Transaction transaction) {
        //process transaction and check if valid, unless block is genesis block then ignore.
        if(transaction == null) return false;
        if((previousHash != "0")) {
            if((transaction.processTransaction() != true)) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }
}
