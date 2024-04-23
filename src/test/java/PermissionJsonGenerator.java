import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 取代前端的mock路由, 解决wangeditor无法在前端项目中, 线上环境无法上传图片的问题
 */
public class PermissionJsonGenerator {
    @Data
    static class Ans {
        private String success;
        private List<Object> data;
    }

    public static void main(String[] args) {
        // Create a Java object representing the given JavaScript object
//        "1".toCharArray()
        PermissionObject permissionObject = createPermissionObject();

        // Convert the Java object to JSON
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Ans ans = new Ans();
            ans.success = "true";
            List<Object> data = new ArrayList<Object>();
            data.add(permissionObject);
            ans.data = data;
            String jsonString = objectMapper.writeValueAsString(ans);
            System.out.println(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private static PermissionObject createPermissionObject() {
        PermissionObject permissionObject = new PermissionObject();
        permissionObject.setPath("/permission");

        PermissionMeta meta = new PermissionMeta();
        meta.setTitle("权限管理");
        meta.setIcon("lollipop");
        meta.setRank(10);
        permissionObject.setMeta(meta);

        // Create children list
        PermissionChild pageChild = new PermissionChild();
        pageChild.setPath("/permission/page/index");
        pageChild.setName("PermissionPage");

        PermissionMeta pageMeta = new PermissionMeta();
        pageMeta.setTitle("页面权限");
        pageMeta.setRoles(new String[]{"admin", "common"});
        pageChild.setMeta(pageMeta);

        PermissionChild buttonChild = new PermissionChild();
        buttonChild.setPath("/permission/button/index");
        buttonChild.setName("PermissionButton");

        PermissionMeta buttonMeta = new PermissionMeta();
        buttonMeta.setTitle("按钮权限");
        buttonMeta.setRoles(new String[]{"admin", "common"});
        buttonMeta.setAuths(new String[]{"btn_add", "btn_edit", "btn_delete"});
        buttonChild.setMeta(buttonMeta);

        permissionObject.setChildren(new PermissionChild[]{pageChild, buttonChild});

        return permissionObject;
    }
}
