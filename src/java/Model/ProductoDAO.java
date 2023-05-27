/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Config.Conexion;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author U20207297
 */
public class ProductoDAO {
    Connection con;
    Conexion cn=new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public List listar(){
    List<Producto>productos=new ArrayList<>();
    String sql="SELECT * FROM producto";
        try {
           con=cn.getConnection();
           ps=con.prepareStatement(sql);
           rs=ps.executeQuery();
            while (rs.next()) {
                Producto p=new Producto();
                p.setId(rs.getInt(1));
                p.setNombres(rs.getString(2));
                p.setCantidad(rs.getInt(3));
                p.setEstado(rs.getString(4));
                p.setFoto(rs.getBinaryStream(5));
                p.setDescripcion(rs.getString(6));
                p.setPrecio(rs.getInt(7));
                productos.add(p);
                
            }
        } catch (Exception e) {
            
        }
        return productos;
    }
    public void listarImg(int id, HttpServletResponse response){
         String sql="select * from producto where idproducto="+id;
         InputStream inputStream =null;
         OutputStream outputStream=null;
         BufferedInputStream bufferedInputStream=null;
         BufferedOutputStream bufferedOutputStream=null;
         
         try {
             outputStream=response.getOutputStream();
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
             if (rs.next()) {
                 inputStream=rs.getBinaryStream("Foto");
             }
            bufferedInputStream=new BufferedInputStream(inputStream);
            bufferedOutputStream=new BufferedOutputStream(outputStream);
            int i=0;
             while ((i=bufferedInputStream.read())!=-1) {
                bufferedOutputStream.write(i);
             }
            
        } catch (Exception e) {
        }
    }
    
}
