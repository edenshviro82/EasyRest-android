package com.example.easyrestapp.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Model {

    private static final Model _instance = new Model();


    public static Model instance(){
        return _instance;
    }

    public ArrayList<Dish> getAllDishes(){
        ArrayList<Dish> arrayAllDishes = new ArrayList<>();
        try {

            String categories= ServerConnection.categoryList().get();
            String[] categoryArray = categories.split(",");
            for (int i = 0; i < categoryArray.length; i++) {
                categoryArray[i] = categoryArray[i].replace("[", "").replace("]", "").replace("\"", "");
                arrayAllDishes.addAll(getDishesByCategory(categoryArray[i]));
            }
        } catch (Exception e) {
            e.getMessage();
        }

        Log.d("server", arrayAllDishes.size() + "");
        return arrayAllDishes;
    }

    public List<Table> getAllTables(){

        List<Table> tableList = new ArrayList<>();
            // Adding first table
            Table table1 = new Table();
            table1.id = "644ea719a3858a31d1eb1b94";
            table1.openTime = "1682876185691";
            table1.update = "1682876185691";
            table1.tableNumber = "7";
            table1.numberOfPeople = 2;
            table1.avgPerPerson = 0;
            table1.restaurantName = "EasyRest";
            table1.fire = false;
            table1.gluten = false;
            table1.lactose = true;
            table1.isVeggie = false;
            table1.comments = new ArrayList<>();
            table1.comments.add("");
            table1.askForWaiter = false;
            table1.orderList = new ArrayList<>();
            tableList.add(table1);
            // Adding second table
            Table table2 = new Table();
            table2.id = "644ea7c7a3858a31d1eb1b98";
            table2.openTime = "1682876359233";
            table2.update = "1682876359233";
            table2.tableNumber = "6";
            table2.numberOfPeople = 1;
            table2.avgPerPerson = 0;
            table2.restaurantName = "EasyRest";
            table2.fire = false;
            table2.gluten = false;
            table2.lactose = false;
            table2.isVeggie = false;
            table2.comments = new ArrayList<>();
            table2.comments.add("");
            table2.askForWaiter = false;
            table2.orderList = new ArrayList<>();
            tableList.add(table2);
            // Adding third table
            Table table3 = new Table();
            table3.id = "644eab53a3858a31d1eb1baf";
            table3.openTime = "1682877267605";
            table3.update = "1682877267605";
            table3.tableNumber = "8";
            table3.numberOfPeople = 2;
            table3.avgPerPerson = 0;
            table3.restaurantName = "EasyRest";
            table3.fire = false;
            table3.gluten = false;
            table3.lactose = false;
            table3.isVeggie = false;
            table3.comments = new ArrayList<>();
            table3.comments.add("");
            table3.askForWaiter = false;
            table3.orderList = new ArrayList<>();
            tableList.add(table3);
            return tableList;

    }

    public Dish getDishById(String id){
        ArrayList<Dish> dishes = this.getAllDishes();
        for(Dish dish : dishes)
        {
            if(dish.getDishId().equals(id)) {
                Log.d("server",dish.toString());
                return dish;

            }
        }
        return new Dish();
    }

    public Table getTableById(String id){

        return null;
    }

    public void addDishToOrder(TableDish td, String tableId){
        ServerConnection.addDishToOrder(tableId,td);
    }

    public ArrayList<Dish> getDishesByCategory(String category) {

        String dishes="";
        try {
            dishes = ServerConnection.getDishByCategory(category).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return parseDishesFromJson(dishes);
    }

    public static ArrayList<Dish> parseDishesFromJson(String json) {
        ArrayList<Dish> dishes = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Dish dish = new Dish();
                dish.dishId = jsonObject.getString("_id");
                dish.dishName = jsonObject.getString("dishName");
                dish.dishCategory = jsonObject.getString("dishCategory");
                dish.dishDescription = jsonObject.getString("dishDescription");
                dish.dishImage = jsonObject.getString("dishImage");
                JSONArray possibleChangesArray = jsonObject.getJSONArray("possibleChanges");
                ArrayList<String> possibleChanges = new ArrayList<>();
                for (int j = 0; j < possibleChangesArray.length(); j++) {
                    possibleChanges.add(possibleChangesArray.getString(j));
                }
                dish.possibleChanges = possibleChanges;
                dish.prepBar = jsonObject.getString("prepBar");
                dish.estimatedPrepTimeRegular = jsonObject.getDouble("estimatedPrepTimeRegular");
                dish.estimatedPrepTimeDuringRushHour = jsonObject.getDouble("estimatedPrepTimeDuringRushHour");
                dish.isGlutenFree = jsonObject.getBoolean("isGlutenFree");
                dish.isVegan = jsonObject.getBoolean("isVegan");
                dish.isVegetarian = jsonObject.getBoolean("isVegetarian");
                dish.isLactoseFree = jsonObject.getBoolean("isLactoseFree");
                dish.dishPrice = jsonObject.getDouble("dishPrice");
                dish.restaurantName = jsonObject.getString("ResturantName");
                dishes.add(dish);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dishes;
    }


//    private Model() {
//        menu = new ArrayList<>();
//        //menu
//        for (int i=0;i<10;i++){
//            menu.add(new Dish("Dish id " + (i+" "), i,"F",true,true,50,"time"));
//        }
//        for (int i=10;i<20;i++){
//            menu.add(new Dish("Dish id " + (i+" "), i,"F",true,true,50,"time"));
//        }
//        for (int i=20;i<30;i++){
//            menu.add(new Dish("Dish id " + (i+" "), i,"F",true,true,50,"time"));
//        }
//        for (int i=30;i<34;i++){
//            menu.add(new Dish("Dish id " + (i+" "), i,"F",true,true,50,"time"));
//        }
//        //tables
//        tables = new ArrayList<>();
//        for (int i=0;i<20;i++){
//            tables.add(new Table(String.valueOf(i), "Note " + i, String.valueOf(i+1), i*10.0, (int)(i*10.0)/(i+1)));
//        }
//    }


}
