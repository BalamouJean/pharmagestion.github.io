package com.starupFX.startup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiServerThread extends Thread
{

    private Socket socket = null;
    String forFicheStock="",fullTextt="",CodeProdui="";
    String[] catfullText={"",""};
 
    public MultiServerThread(Socket socket) {
        super("KKMultiServerThread");
        this.socket = socket;
    }
     
    public void run() {
 
        try {

            BufferedReader ClientInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            fullTextt=ClientInput.readLine();
            catfullText=fullTextt.split("/");
            forFicheStock=catfullText[0];
            CodeProdui=catfullText[1];
            
    			FileWriter modifier=new FileWriter(new File("C:\\pharmacieDOC\\pharmaPDF\\FicheDeStocks\\"+CodeProdui+".dat"),true);
    			BufferedWriter bw=new BufferedWriter(modifier);
    			bw.write(forFicheStock);
    			bw.newLine();
    			bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
