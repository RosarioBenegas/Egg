package com.egg.biblioteca.controladores;


import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.EditorialServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")   //localhost:8080/editorial
public class EditorialControlador {

    @Autowired
    EditorialServicio editorialServicio;

    @GetMapping("/registrar")   //localhost:8080/editorial/registrar
    public String registrar() {
        return "editorialForm.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {

        try {

            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "la editorial se ha registrado correctamente");

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "editorialForm.html";
        }

        return "index.html";

    }
    
    @GetMapping("/lista")
     public String listar(ModelMap modelo){
         
         List<Editorial> editoriales = editorialServicio.listarEditoriales();
         modelo.addAttribute("editoriales", editoriales);
        return "editorialList.html";
          
      }

     @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {

        modelo.put("editorial", editorialServicio.getOne(id));
        return "editorialModificar.html";
    }
    
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo) {

        try {
            editorialServicio.modificarEditorial(nombre, id);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "editorialModificar.html";
        }
    }
}
