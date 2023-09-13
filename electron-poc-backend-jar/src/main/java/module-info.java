module electron.poc.backend.jar {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires spring.web;
    requires spring.data.jpa;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires org.apache.tomcat.embed.core;

    opens poc;
    opens poc.dto;
    opens poc.api;
    opens poc.service;
}