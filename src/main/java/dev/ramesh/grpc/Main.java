package dev.ramesh.grpc;

import dev.ramesh.grpc.models.Person;
import lombok.extern.slf4j.Slf4j;





@Slf4j
public class Main {


    record Person(int id, String first_name,String last_name,String email, Long phone_number){

    }


    public static void main(String[] args) {

    }

    private static void proto(dev.ramesh.grpc.models.Person person){

    }


    private static void json(Person person){

    }
}