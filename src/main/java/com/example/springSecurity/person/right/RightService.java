package com.example.springSecurity.person.right;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RightService {
    private final RightRepository rightRepository;

    @Autowired
    public RightService(RightRepository rightRepository) {
        this.rightRepository = rightRepository;
    }

    public List<RightEntity> getRight(){
        return rightRepository.findAll();
    }

    public RightEntity addNewRight(RightEntity right) {

        if ( right.getRightName() != null &&
                right.getRightName().length() > 0 ){
            Optional<RightEntity> rightOptional = rightRepository.findRightByRightName(right.getRightName());
            if (rightOptional.isPresent()) {
                throw new IllegalStateException("This Right is registered before!");
            }
            rightRepository.save(right);
        } else throw new IllegalStateException("The name of right can not be empty!");
        return right;
    }

    public void deleteRight(Integer rightId) {
        boolean exists = rightRepository.existsById(rightId);
        if (!exists) {
            throw new IllegalStateException("This Person whit id "+ rightId+" do not exists!");
        }
        rightRepository.deleteById(rightId);
    }
    @Transactional
    public RightEntity updateRight(Integer rightId,
                                   RightEntity newRight) {
        RightEntity updareRight = rightRepository.findById(rightId)
                .orElseThrow(() -> new IllegalStateException("person with ID " + rightId + " do not exists!"));

        if ( newRight.getRightName() != null &&
                newRight.getRightName().length() > 0 &&
                !Objects.equals(updareRight.getRightName(), newRight.getRightName())) {
            Optional <RightEntity> rightEntityOptional = rightRepository.findRightByRightName(newRight.getRightName());
            if (rightEntityOptional.isPresent()) {
                throw new IllegalStateException("This right is registered before!");
            }
            updareRight.setRightName(newRight.getRightName());
        }
        return updareRight;
    }
}
