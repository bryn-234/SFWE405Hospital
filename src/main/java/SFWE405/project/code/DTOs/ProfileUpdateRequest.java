package SFWE405.project.code.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


/**
 * 
 * Created for controller requests made to update profile.
 * Does not contain all information about a user, only the attributes
 * that verified users should be able to update. (password, username, email) 
 * 
 *  @author Joseph Corella 
 */

@Data
@AllArgsConstructor
@Builder
public class ProfileUpdateRequest {
    private String password;
    private String email;
    private String username;


}
