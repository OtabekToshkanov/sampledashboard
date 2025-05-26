package uz.greenwhite.sampledashboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserContext {
    private String sub;
    private long companyId;
    private long userId;
    private String username;
    private String name;
}
