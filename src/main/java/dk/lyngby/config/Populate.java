package dk.lyngby.config;


import dk.lyngby.model.Role;
import dk.lyngby.model.User;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class Populate {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        createUserTestData(emf);
    }
    public static void createUserTestData(EntityManagerFactory emf) {

        User user = new User("user", "user123");
        User admin = new User("admin", "admin123");
        User manager = new User("manager", "manager123");
        User supervisor = new User("supervisor", "supervisor123");

        Role userRole = new Role(Role.RoleName.USER);
        Role adminRole = new Role(Role.RoleName.ADMIN);
        Role managerRole = new Role(Role.RoleName.MANAGER);
        Role supervisorRole = new Role(Role.RoleName.SUPERVISOR);

        user.setRoleList(Set.of(userRole));
        admin.setRoleList(Set.of(adminRole));
        manager.setRoleList(Set.of(managerRole));
        supervisor.setRoleList(Set.of(supervisorRole));

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(managerRole);
            em.persist(supervisorRole);
            em.persist(user);
            em.persist(admin);
            em.persist(manager);
            em.persist(supervisor);
            em.getTransaction().commit();
        }
    }
}
