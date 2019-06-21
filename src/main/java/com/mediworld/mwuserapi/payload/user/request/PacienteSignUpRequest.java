package com.mediworld.mwuserapi.payload.user.request;

import com.mediworld.mwuserapi.payload.user.request.BaseSignUpRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * <h1>PacienteSignUpRequest</h1>
 * Payload de tipo Request
 * Clase que representa un payload para la peticion a la api, para el registro del user
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
public class PacienteSignUpRequest extends BaseSignUpRequest {
    @NotBlank
    @Size(max = 30, min = 3)
    private String username;

    private Date fechaNacimiento;
}
