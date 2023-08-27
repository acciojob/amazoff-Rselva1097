package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    Map<String,Order> orderDB=new HashMap<>();
    Map<String,DeliveryPartner> deliverPartnerDB=new HashMap<>();
    Map<String, List<String>> orderPartnerDB=new HashMap<>();

    public void addOrder(Order order) {
        if(orderDB.containsKey(order.getId()) == false){
            orderDB.put(order.getId(),order);
        }
    }

    public void addPartner(String partnerId) {
        if(deliverPartnerDB.containsKey(partnerId) == false){
            deliverPartnerDB.put(partnerId,new DeliveryPartner(partnerId));
        }
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
      if(orderDB.containsKey(orderId) && deliverPartnerDB.containsKey(partnerId)){
          if(orderPartnerDB.containsKey(partnerId) == false){
              Order order=orderDB.get(orderId);
              int orderCnt=deliverPartnerDB.get(partnerId).getNumberOfOrders();
              deliverPartnerDB.get(partnerId).setNumberOfOrders(orderCnt+1);
              List<String> orderList=new ArrayList<>();
              orderList.add(order.getId());
              orderPartnerDB.put(partnerId,orderList);
          }
          else{
              Order order=orderDB.get(orderId);
              int orderCnt=deliverPartnerDB.get(partnerId).getNumberOfOrders();
              deliverPartnerDB.get(partnerId).setNumberOfOrders(orderCnt+1);
              List<String> orderList=orderPartnerDB.get(partnerId);
              orderList.add(order.getId());
              orderPartnerDB.put(partnerId,orderList);
          }
      }
    }

    public Order getOrderById(String orderId) {
        if(orderDB.containsKey(orderId) == true){
            return orderDB.get(orderId);
        }

        return null;
    }

    public DeliveryPartner getPartnerById(String partnerId) {
       if(deliverPartnerDB.containsKey(partnerId) == true){
           return deliverPartnerDB.get(partnerId);
       }

       return null;
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
      if(deliverPartnerDB.containsKey(partnerId) == true){
          return deliverPartnerDB.get(partnerId).getNumberOfOrders();
      }

      return null;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
       if(orderPartnerDB.containsKey(partnerId) == true){
           return orderPartnerDB.get(partnerId);
       }

       return null;
    }

    public List<String> getAllOrders() {
      List<String> allOrders= (List<String>) orderDB.keySet();
        return allOrders;
    }

    public Integer getCountOfUnassignedOrders() {
        int ans=0;
        List<String> totalNumberOfOrders= (List<String>) orderDB.keySet();

        for(String partner : orderPartnerDB.keySet()){
            List<String> assignedOrders=orderPartnerDB.get(partner);

            for (String it : assignedOrders){
                if(totalNumberOfOrders.contains(it) == true){
                    totalNumberOfOrders.remove(it);
                }
            }
        }

        if(totalNumberOfOrders.size() > 0){
            return totalNumberOfOrders.size();
        }

        return null;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        String[] split=time.split(":",0);
        int h=Integer.parseInt(split[0]);
        int m=Integer.parseInt(split[1]);

        int timeInMin=h * 60 + m;
        int ans=0;

        if(orderPartnerDB.containsKey(partnerId)){
            List<String> listOfOrders=orderPartnerDB.get(partnerId);

            for(String order : listOfOrders){
                if(orderDB.containsKey(order)){
                    int deliveryTime=orderDB.get(order).getDeliveryTime();

                    if(deliveryTime > timeInMin){
                        ans++;
                    }
                }
            }
        }

        return ans;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
       if(orderPartnerDB.containsKey(partnerId)){
           List<String> listOfOrder=orderPartnerDB.get(partnerId);
           int max=Integer.MIN_VALUE;

           for(String order : listOfOrder){
               max=Math.max(max,orderDB.get(order).getDeliveryTime());
           }
           int h=max/60;
           int m=max%60;

           String ans="";
           if(h/10 > 0){
               ans+=h;
               ans+=':';
           }
           else{
               ans+=0;
               ans+=h;
               ans+=':';
           }

           if(m/10 > 0){
               ans+=m;
           }
           else{
               ans+=0;
               ans+=m;

           }
           return ans;
        }

       return null;
    }
}
