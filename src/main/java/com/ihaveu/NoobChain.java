package com.ihaveu;

import com.google.gson.GsonBuilder;
import com.ihaveu.block.Block;
import com.ihaveu.util.StringUtil;
import com.ihaveu.wallet.Transaction;
import com.ihaveu.wallet.TransactionOutput;
import com.ihaveu.wallet.Wallet;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ZBK on 2018-03-31.
 */
public class NoobChain {
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); //list of all unspent transactions.
    public static int difficulty = 5;
    public static int minimumTransaction =2;
    public static Wallet walletA;
    public static Wallet walletB;
    public static void main(String[] args) {


        //add our blocks to the blockchain ArrayList:
//        blockchain.add(new Block("Hi im the first block", "0"));
//        blockchain.add(new Block("Yo im the second block",blockchain.get(blockchain.size()-1).hash));
//        blockchain.add(new Block("Hey im the third block",blockchain.get(blockchain.size()-1).hash));
//
//        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
//        System.out.println(blockchainJson);


//     增加工作证明
//        blockchain.add(new Block("Hi im the first block", "0"));
//        System.out.println("Trying to Mine block 1... ");
//        blockchain.get(0).mineBlock(difficulty);
//
//        blockchain.add(new Block("Yo im the second block",blockchain.get(blockchain.size()-1).hash));
//        System.out.println("Trying to Mine block 2... ");
//        blockchain.get(1).mineBlock(difficulty);
//
//        blockchain.add(new Block("Hey im the third block",blockchain.get(blockchain.size()-1).hash));
//        System.out.println("Trying to Mine block 3... ");
//        blockchain.get(2).mineBlock(difficulty);
//
//        System.out.println("\nBlockchain is Valid: " + isChainValid());
//
//        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
//        System.out.println("\nThe block chain: ");
//        System.out.println(blockchainJson);
//       Setup Bouncey castle as a Security Provider
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        //Create the new wallets
        walletA = new Wallet();
        walletB = new Wallet();
        //Test public and private keys
        System.out.println("Private and public keys:");
        System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
        System.out.println(StringUtil.getStringFromKey(walletA.publicKey));

        //Create a test transaction from WalletA to walletB
        Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
        transaction.generateSignature(walletA.privateKey);
        //Verify the signature works and verify it from the public key
        System.out.println("Is signature verified");
        System.out.println(transaction.verifiySignature());
    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;

        //loop through blockchain to check hashes:
        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                System.out.println("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
        }
        return true;
    }

}
