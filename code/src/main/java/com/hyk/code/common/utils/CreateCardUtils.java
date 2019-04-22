package com.hyk.code.common.utils;

import com.hyk.code.modules.hyk.entity.HykRechargeCard;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 霍中曦
 * @Date: 2018/12/19 11:40
 * @Description:
 */
public class CreateCardUtils {



    public static synchronized List<HykRechargeCard> createCard(HykRechargeCard obj){
        try{
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String time=(System.currentTimeMillis()+"").substring(1,10);
        String cardno;
        String random="";
        List<HykRechargeCard> list=new ArrayList<HykRechargeCard>();
        for(int i=1;i<=obj.getCardNum();i++){
            random=((int)((Math.random()*9+1)*1000))+"";
            if(i<10){
                cardno=time+"00"+i;
            }else if(i<100){
                cardno=time+"0"+i;
            }else{
                cardno=time+i;
            }
            cardno=cardno+random;
            HykRechargeCard newObj=new HykRechargeCard();
            newObj.setCode(obj.getCode());
            newObj.setMoney(obj.getMoney());
            newObj.setCaredno(cardno);
            newObj.setPassword(cardno.substring(10,cardno.length()));
            newObj.setUseMethod(obj.getUseMethod());
            newObj.setSaleStatus(obj.getSaleStatus());
            newObj.setCreateDate(obj.getCreateDate());
            newObj.setOverDate(obj.getOverDate());
            newObj.setDays(obj.getDays());
            newObj.setStatus(obj.getStatus());
            newObj.setRemarks(obj.getRemarks());
            newObj.setDelFlag("0");
            newObj.preInsert();
            list.add(newObj);
        }

        return list;
    }

    public static void main(String args[]){
            HykRechargeCard card=new HykRechargeCard();
            card.setCardNum(100);
            List<HykRechargeCard> list=createCard(card);

            for(int i=0;i<list.size();i++){
                System.out.print(list.get(i).getId());
                System.out.print("=="+list.get(i).getCaredno());
                System.out.println("=="+list.get(i).getPassword());
            }
    }

}
