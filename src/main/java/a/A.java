package a;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

import java.util.Scanner;

public class A {

    public static void main(String[] args) {
        IniSecurityManagerFactory securityManagerFactory = new IniSecurityManagerFactory("user1.ini");
        SecurityManager securityManager = securityManagerFactory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Scanner scanner = new Scanner(System.in);
        Subject subject;
        while (true) {
            System.out.println("username:");
            String username = scanner.next();
            System.out.println("password:");
            String password = scanner.next();
            subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                subject.login(token);
                System.out.println("登录成功");
                System.out.println("welcome, " + token.getUsername());
                break;
            } catch (AuthenticationException e) {
                System.out.println("登录失败");
            }
        }
        while (true) {
            System.out.println("1.admin's space");
            System.out.println("2.u1's space");
            System.out.println("3.u2's space");
            System.out.println("4.guest's space");
            System.out.println("5.quit");
            String choice = scanner.next();
            switch (choice) {
                case "1" -> admin();
                case "2" -> u1();
                case "3" -> u2();
                case "4" -> guest();
                case "5" -> System.exit(1);
                default -> System.out.println("wrong input.");
            }
        }
    }

    @RequiresRoles("admin_role")
    public static void admin() {
        System.out.println("you're in admin's sapce, welcome");
    }

    @RequiresPermissions("sys:u1")
    public static void u1() {
        System.out.println("you're in u1's space, welcome");
    }

    @RequiresPermissions("sys:u2")
    public static void u2() {
        System.out.println("you're in u2's space, welcome");
    }

    @RequiresPermissions("sys:guest")
    public static void guest() {
        System.out.println("you're in guest's space, welcome");
    }
}
