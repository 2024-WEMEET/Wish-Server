package wish.wishServer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wish.wishServer.common.response.BasicResponse;
import wish.wishServer.common.response.ResponseUtil;

@RestController
@RequestMapping("/health")
public class HealthCheck {

    @GetMapping
    public BasicResponse<String> healthCheck() {
        return ResponseUtil.success("health check");
    }
}
