package com.mediworld.mwuserapi.resources;

import com.mediworld.mwuserapi.model.Paciente;
import com.mediworld.mwuserapi.resources.vo.PacienteVO;
import com.mediworld.mwuserapi.services.IPacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <h1>PacienteResource</h1>
 * Controlador rest que recibe request y envia datos al frontend que lo requiera
 * dadas las urls
 * @see com.mediworld.mwuserapi.services.IPacienteService
 * @author Eduardo Rasgado Ruiz
 */
@RestController
@RequestMapping("/api/paciente")
public class PacienteResource {

    private final IPacienteService pacienteService;

    public PacienteResource(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    /**
     * Metodo para crear un nuevo paciente con datos del frontend
     * @param pacienteVo
     * @return
     */
    @PostMapping
    public ResponseEntity<Paciente> create(@RequestBody PacienteVO pacienteVo) {
        Paciente paciente = new Paciente();
        paciente = this.mappingPacienteUtil(paciente, pacienteVo);

        paciente = this.pacienteService.create(paciente);
        if(paciente != null) {
            return new ResponseEntity<>(paciente, HttpStatus.CREATED);
        }
        // error al intentar crear el nuevo paciente
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Metodo para actualizar un paciente ya existente con datos del frontend
     * @param idPaciente
     * @param pacienteVo
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> update(@PathVariable("id") String idPaciente,
                                           @RequestBody PacienteVO pacienteVo) {
        Paciente paciente = this.pacienteService.getById(idPaciente);
        if(paciente != null) {
            paciente = this.mappingPacienteUtil(paciente, pacienteVo);
            paciente = this.pacienteService.update(paciente);
            return new ResponseEntity<>(paciente, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo para eliminar un paciente con el id del paciente proveniente del frontend
     * @param idPaciente
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Paciente> delete(@PathVariable("id") String idPaciente) {
        Paciente paciente = this.pacienteService.getById(idPaciente);
        if(paciente != null) {
            if(this.pacienteService.delete(paciente)){
                return new ResponseEntity<>(paciente, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo para devolver todas las entidades de paciente al frontend
     * @return una lista de pacientes
     */
    @GetMapping
    public ResponseEntity<List<Paciente>> getAll() {
        return ResponseEntity.ok(this.pacienteService.getAll());
    }

    // -------------------------SECCION DE UTILERIA------------------------

    /**
     * Metodo de utileria para mapear los campos que viene del paciente de objeto
     * virtual a la instancia del paciente que va a ser manipulado en el servicio
     * @param paciente paciente completo
     * @param pacienteVo paciente virtual
     * @return paciente con campos llenos
     */
    private Paciente mappingPacienteUtil(Paciente paciente, PacienteVO pacienteVo){
        paciente.setUsername(pacienteVo.getUsername());
        paciente.setEmail(pacienteVo.getEmail());
        paciente.setPassword(pacienteVo.getPassword());
        paciente.setNombre(pacienteVo.getNombre());
        paciente.setApellidos(pacienteVo.getApellidos());
        paciente.setFechaNacimiento(pacienteVo.getFechaNacimiento());
        paciente.setGenero(pacienteVo.isGenero());
        //paciente.setCreatedAt(new Date());

        return paciente;
    }
}
