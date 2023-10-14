package dk.lyngby.dao;

import dk.lyngby.exception.ApiException;
import dk.lyngby.exception.AuthorizationException;
import dk.lyngby.model.Role;
import dk.lyngby.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class AuthDao {

    private static AuthDao instance;
    private static EntityManagerFactory emf;

    public static AuthDao getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AuthDao();
        }
        return instance;
    }

    public void registerUser(String userName, String userPassword, Set<String> roleList) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = new User(userName, userPassword);
            Set<Role> roles = user.getRoleList();

            for (String roleName : roleList) {
                Role role = em.find(Role.class, Role.RoleName.valueOf(roleName));
                if (role != null) roles.add(role);
            }

            em.persist(user);
            em.getTransaction().commit();
        }
    }

    public void checkUser(String userName) throws ApiException {

        try (var em = emf.createEntityManager())
        {
            User user = em.find(User.class, userName);

            if(user != null) throw new ApiException(400, "User already exists");
        }
    }

    public void checkRoles(Set<String> roleList) throws ApiException {
        Role.RoleName[] roleNames = Role.RoleName.values();

        for (String roleName : roleList) {
            boolean roleExists = false;
            for (Role.RoleName role : roleNames) {
                if (roleName.equals(role.toString())) {
                    roleExists = true;
                    break;
                }
            }
            if (!roleExists) throw new ApiException(400, "Role does not exist");
        }
    }

    public User verifyUser(String username, String password) throws AuthorizationException {

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, username);

            if (user == null || !user.verifyPassword(password)) {
                throw new AuthorizationException(401, "Invalid user name or password");
            }
            em.getTransaction().commit();
            return user;
        }
    }
}
