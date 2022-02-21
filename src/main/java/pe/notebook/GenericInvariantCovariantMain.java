package pe.notebook;

import java.util.ArrayList;
import java.util.List;

//공변, 반공변
public class GenericInvariantCovariantMain {
    //PECS // 데이터를 제공하면 Extend, 값을 사용함면 Super

    public static void main(String[] args) {
        Zookeepers zk = new Zookeepers();
        Cage<Dog> dog = new Cage<>();
        zk.give(dog);

        //Consumer is Super
        Cage<Carnivora> ct = new Cage<>();
        Cage<? super Cat> cage = ct;
         cage.push(new Cat());
    }
}


class Animal {}
class Carnivora extends Animal {}
class Dog extends Carnivora {}
class Cat extends Carnivora {}

class Cage<T> {
    private final List<T> animals = new ArrayList<>();

    public void push(T animal){
        this.animals.add(animal);
    }

    public List<T> getAll(){
        return animals;
    }

}


class Zookeepers {
    //Procedure is extends 를 사용
    public void give(Cage<? extends Carnivora> cage){
        List<? extends Carnivora> cs = cage.getAll();
    }
}
