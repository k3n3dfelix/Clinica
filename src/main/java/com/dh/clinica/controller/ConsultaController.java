package com.dh.clinica.controller;

import com.dh.clinica.model.Consulta;
import com.dh.clinica.model.Dentista;
import com.dh.clinica.service.ConsultaService;
import com.dh.clinica.service.DentistaService;
import com.dh.clinica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private DentistaService dentistaService;
    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    public ResponseEntity<Consulta> cadastrar(@RequestBody Consulta consulta) {
        ResponseEntity<Consulta> response = null;
        if (!(consulta.getId() == null || consulta.getPaciente()== null || consulta.getDentista()== null || consulta.getDate()== null)){
                consultaService.cadastrar(consulta);
                response = ResponseEntity.ok(consulta);
                } else {
            response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> buscarTodos(){
        return ResponseEntity.ok(consultaService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Consulta>> buscarPorId(@PathVariable Integer id) {
        if(consultaService.buscarPorId(id).isPresent()){
            return ResponseEntity.ok(consultaService.buscarPorId(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping
    public ResponseEntity<Consulta> atualizar(@RequestBody Consulta consulta){
        return ResponseEntity.ok(consultaService.atualizar(consulta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Integer id){
        ResponseEntity<String> response;
        if(consultaService.buscarPorId(id).isPresent()){
            consultaService.excluir(id);
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Consulta excluída com sucesso!");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma consulta encontrada!");
        }
        return response;
    }

//    public boolean validacaoAtributo(Consulta consulta){
//        if (consulta.getDate().isEmpty()  || consulta.getDate().isBlank()){
//            return false;
//        }
//        return true;
//    }
}
