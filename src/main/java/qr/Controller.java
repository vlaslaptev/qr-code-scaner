package qr;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/qr")
public class Controller {
    private static final String FAILED_MESSAGE = "failed read qr-code";
    private final Reader qrReader;

    public Controller(Reader reader) {
        this.qrReader = reader;
    }

    @PostMapping("/one")
    public String qrCode(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            return qrReader.readQrCode(file.getInputStream());
        } catch (Exception e) {
            return FAILED_MESSAGE;
        }
    }

    @PostMapping("/multi")
    public String qrCodeMulti(@RequestParam("files") MultipartFile[] files, RedirectAttributes redirectAttributes) {
        StringBuilder result = new StringBuilder();
        int index = 0;
        for (MultipartFile file : files) {
            index++;
            result.append(index).append(":  ");
            try {
                result.append(qrReader.readQrCode(file.getInputStream())).append(" \n");
            } catch (Exception e) {
                result.append(FAILED_MESSAGE + "\n");
            }
        }
        return result.toString();
    }
}
