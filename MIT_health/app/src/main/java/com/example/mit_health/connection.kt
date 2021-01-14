package com.example.mit_health//package com.example.mit_health
//
//import java.sql.Connection
//import java.sql.Driver
//import java.sql.DriverManager
//
//class connection {
//
//    internal var conexion:Connection ?= null
//
//    fun conexionBD():Connection {
//        try
//        {
//            Class.forName("org.posrgresql.Driver")
//            conexion = DriverManger.getConnection("jdbc:postgresql://192.168.1.230:5432/postgres")
//        }
//        catch (er:Exception) {
//            System.err.println(er.message)
//        }
//        return conexion
//    }
//
//    @Throws(Exception::class)
//    protected fun carrar_conexion(con:Connection) {
//        con.close()
//    }
//}
//
//
