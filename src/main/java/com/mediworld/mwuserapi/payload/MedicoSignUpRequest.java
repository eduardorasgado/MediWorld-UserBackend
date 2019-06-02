package com.mediworld.mwuserapi.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <h1>MedicoSignUpRequest</h1>
 * Payload que parsea la informacion que viene del registro de un medico
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
public class MedicoSignUpRequest extends BaseSignUpRequest{
    //
}
