package org.springframework.samples.SevenIslands.board;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    

    @GetMapping()
    public String board(Map<String, Object> model, HttpServletResponse response) {
        String vista = "boards/board";
        // response.addHeader("Refresh","1");
        model.put("now", new Date());
		model.put("board",boardService.findById(1).get());
        // modelMap.addAttribute("board", boardService.findById(1).get());
        System.out.println(boardService.findById(1).get().toString());
        return vista;
    }

    
}
