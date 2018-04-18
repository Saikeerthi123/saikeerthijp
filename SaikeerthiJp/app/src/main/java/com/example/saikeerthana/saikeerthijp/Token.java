package com.example.saikeerthana.saikeerthijp;

/**
 * Created by saikeerthana on 02-04-2018.
 */

public class Token {
    String tokenId;
    String userName;
    String userPhone;
    String inputTime;
    String userAddress;

    public Token(){

    }

    public Token(String inputTime,String tokenId,String userAddress,String userName,String userPhone){
        this.inputTime=inputTime;
        this.tokenId=tokenId;
        this.userAddress=userAddress;
        this.userName=userName;
        this.userPhone=userPhone;
    }

    public String getInputTime() {
        return inputTime;
    }

    public String getTokenId() {
        return tokenId;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

}
