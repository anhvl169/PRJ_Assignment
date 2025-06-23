/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.authentication.BaseRBACController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.Account;
import model.Feature;
import model.Role;

/**
 *
 * @author vulea
 */
@WebServlet("/home")
public class HomeServlet extends BaseRBACController{

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        ArrayList<Feature> features = new ArrayList<>();
        //lấy tất cả các feature từ role của account thêm vào features array để hiển thị
        for (Role role : account.getRoles()) {
            for (Feature f : role.getFeatures()) {
                if(!features.contains(f));
                features.add(f);
            }
        }
        req.setAttribute("features", features);
        req.getRequestDispatcher("./view/home.jsp").forward(req, resp);
    }
    
}
