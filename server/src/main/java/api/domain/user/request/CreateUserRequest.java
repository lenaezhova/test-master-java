package api.domain.user.request;

public record CreateUserRequest (
        String password,
        String name,
        String email
) {
}
