package com.github.gabrielgua.websocket_chat.infra.specs;

import com.github.gabrielgua.websocket_chat.domain.model.Chat;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Join(path = "users", alias = "users")
@And({
        @Spec(path = "users.id", params = "user", spec = Equal.class)
})
public interface ChatSpecification extends Specification<Chat> {
}
