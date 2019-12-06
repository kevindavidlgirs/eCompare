/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniproject;

/**
 *
 * @author lasynsec
 */
public class InvalidTransferException extends RuntimeException {

    public InvalidTransferException(String message) {
       super(message);
    }
    
}
