package com.example.springSecurity.person.right;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/right")
public class RightController {
    public final RightService rightService;
    public final RightRepository rightRepository;

    @Autowired
    public RightController(RightService rightService, RightRepository rightRepository) {
        this.rightService = rightService;
        this.rightRepository = rightRepository;
    }

    @GetMapping
    public List<RightEntity> getRight() {
        return rightService.getRight();
    }

    @GetMapping (path = "{rightId}")
    public RightEntity getRight(@PathVariable("rightId") Integer rightId){
        RightEntity right = getRight().stream()
                .filter(rights -> rightId.equals(rights.getRightId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Right with id" + rightId + "does not exist !"));
        return right;
    }

    @PostMapping
    public ResponseEntity registerNewRight(@RequestBody RightEntity right){

        rightService.addNewRight(right);
        return ResponseEntity.ok().body(right);
    }

    @DeleteMapping(path = "{rightId}")
    public void deleteRight(@PathVariable("rightId") Integer rightId) {

        rightService.deleteRight(rightId);
    }

    @PutMapping(path = "{rightId}")
    public ResponseEntity editRight(
            @PathVariable("rightId") Integer rightId,
            @RequestBody RightEntity newRight){
        return ResponseEntity.ok().body(rightService.updateRight(rightId,newRight));
    }
}
