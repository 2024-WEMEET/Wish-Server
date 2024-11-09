package wish.wishServer.certification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Certification")
public class CertificationController {

    @PostMapping
    public ResponseEntity<List<Map<String, String>>> addCertifications(@RequestBody List<Map<String, String>> certifications) {
        return ResponseEntity.ok(certifications);
    }

}
