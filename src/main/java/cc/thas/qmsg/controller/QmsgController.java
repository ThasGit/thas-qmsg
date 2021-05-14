package cc.thas.qmsg.controller;

import cc.thas.qmsg.service.QmsgSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class QmsgController {


    @Autowired
    private QmsgSendService qmsgSendService;

    @RequestMapping("/send/{token3}")
    public int send(@RequestParam(value = "token1", required = false) String token1,
                    @RequestHeader(value = "token2", required = false) String token2,
                    @PathVariable(value = "token3", required = false) String token3, String msg) {
        String token = token3 != null ? token3 : (token2 != null ? token2 : token1);
        int result = qmsgSendService.send(token, msg);
        return result;
    }
}
