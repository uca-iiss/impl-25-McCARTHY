package com.example;

import com.example.service.MyComponent;
import com.example.injector.SimpleInjector;

public class App {
    public static void main(String[] args) {
        MyComponent component = new MyComponent();
        SimpleInjector.inject(component);
        component.run();
    }
}
