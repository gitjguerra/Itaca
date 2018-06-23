package com.csi.itaca.load.step;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
 
 
public class Reader implements ItemReader<String>{

    //TODO: Añadir acá la lectura de archivos, es necesario discriminar por el type csv, xml, txt, excel
    private String[] messages = {"prueba itaca", "row1", "row2", "row3", "row4"};
     
    private int count=0;
     
    Logger logger = LoggerFactory.getLogger(this.getClass());
     
    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
         
        if(count < messages.length){
            return messages[count++];
        }else{
            count=0;
        }
        return null;
    }
     
}