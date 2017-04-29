/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



class Listenertest extends Thread {
  @Override
  public void run(){
  System.out.println("Listener thread is running.");
  }
}


public class ThreadTest {
  public static void main(String[] args) {
    Listenertest listen = new Listenertest();
  }
}
