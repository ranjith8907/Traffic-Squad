package com.example.trafficsquad;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.*;
import java.lang.*;
import java.io.*;


class MST {
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reference=database.getReference("Track Criminals");
    private static final int V = 5;

    int minKey(int key[], Boolean mstSet[]) {

        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++)
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }

    void printMST(int parent[], int graph[][],String destination[],String destination1[],String location,String vehicleno, String name) {
        Map<String, String> map = new HashMap<>();
        String end = "ARAPALAYAM";
        String start="THIRUPURAKUNDROM";
        String place[]=new String[5];
        int l=0;
        for (int i = 1; i < V; i++) {
            String j = Integer.toString(i);
            if(destination1[i].equals(location)) {
                System.out.println("else if"+destination[i] + " - " + destination1[i] + "\t" + graph[i][parent[i]]);
                map.put("1","");
                map.put("4","");
                map.put(j, destination[i] + " - " + destination1[i] + "\t" + graph[i][parent[i]]);
                place[l]=destination[i];
                l++;
                place[l]=destination1[i];
                l++;
                break;
            }
            if(destination[i].equals(end) && !destination[i].equals(start)) {
                System.out.println("if" + destination[i] + " - " + destination1[i] + "\t" + graph[i][parent[i]]);
                map.put(j, destination[i] + " - " + destination1[i] + "\t" + graph[i][parent[i]]);
                place[l]=destination[i];
                l++;
            }
            else if(!destination1[i].equals("ARAPALAYAM")){
               System.out.println("else"+destination[i] + " - " + destination1[i] + "\t" + graph[i][parent[i]]);
               map.put(j, destination[i] + " - " + destination1[i] + "\t" + graph[i][parent[i]]);
                place[l]=destination[i];
                l++;
            }
            end=destination1[i];
            start=destination[i];
        }
        int i=0;
        while(place[i]!=null){
            map.put("VEHICLE NO:", vehicleno);
            map.put("NAME:", name);
            if(place == null){
                break;
            }
            else {
                reference.child(place[i]).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        } else {

                        }

                    }
                });
            }
            i++;
        }
    }
    void primMST(int graph[][],String destination[],String location,String vehicleno,String name) {

        int parent[] = new int[V];
        String destination1[]=new String[V];


        int key[] = new int[V];


        Boolean mstSet[] = new Boolean[V];


        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }


        key[0] = 0;

        parent[0] = -1;
        destination1[0]="\0";


        for (int count = 0; count < V - 1; count++) {

            int u = minKey(key, mstSet);


            mstSet[u] = true;


            for (int v = 0; v < V; v++)


                if (graph[u][v] != 0 && mstSet[v] == false && graph[u][v] < key[v]) {
                    parent[v] = u;
                    destination1[v]=destination[u];
                    key[v] = graph[u][v];
                }
        }

        printMST(parent, graph, destination1,destination,location,vehicleno,name);
    }
}



