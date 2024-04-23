import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
class PermissionMeta {
    String title;
    String icon;
    int rank;
    String[] roles;
    String[] auths;
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
class PermissionChild {
    String path;
    String name;
    PermissionMeta meta;
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PermissionObject {
    String path;
    PermissionMeta meta;
    PermissionChild[] children;

    public static void main(String[] args) {
        PermissionObject permissionObject = createPermissionObject();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(permissionObject);
            System.out.println(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private static PermissionObject createPermissionObject() {
        PermissionObject permissionObject = new PermissionObject();
        permissionObject.path = "/permission";

        PermissionMeta meta = new PermissionMeta();
        meta.title = "权限管理";
        meta.icon = "lollipop";
        meta.rank = 10;
        permissionObject.meta = meta;

        PermissionChild[] children = new PermissionChild[2];

        PermissionChild pageChild = new PermissionChild();
        pageChild.path = "/permission/page/index";
        pageChild.name = "PermissionPage";

        PermissionMeta pageMeta = new PermissionMeta();
        pageMeta.title = "页面权限";
        pageMeta.roles = new String[]{"admin", "common"};
        pageChild.meta = pageMeta;

        PermissionChild buttonChild = new PermissionChild();
        buttonChild.path = "/permission/button/index";
        buttonChild.name = "PermissionButton";

        PermissionMeta buttonMeta = new PermissionMeta();
        buttonMeta.title = "按钮权限";
        buttonMeta.roles = new String[]{"admin", "common"};
        buttonMeta.auths = new String[]{"btn_add", "btn_edit", "btn_delete"};
        buttonChild.meta = buttonMeta;

        children[0] = pageChild;
        children[1] = buttonChild;

        permissionObject.children = children;

        return permissionObject;
    }
}
