package org.energygrid.east.userservice;

import org.energygrid.east.userservice.service.SecurityService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SecurityServiceTest {
    private final SecurityService securityService;

    public SecurityServiceTest() {
        securityService = new SecurityService();
    }

    @Test
    public void HashPasswordTest() {
        String passwordHash = securityService.HashPassword("Test");
        Assert.assertNotNull(passwordHash);
    }

    @Test
    public void HashPasswordIllegalArgumentExceptionTest() {
        Assert.assertThrows(IllegalArgumentException.class, () -> securityService.HashPassword(null));
    }

    @Test
    public void VerifyPasswordTest() {
        String passwordHash = securityService.HashPassword("123");
        boolean success = securityService.Verify("123", passwordHash);
        Assert.assertTrue(success);
    }

    @Test
    public void VerifyPasswordIllegalArgumentExceptionTest() {
        Assert.assertThrows(IllegalArgumentException.class, () -> securityService.Verify(null ,null));
    }

    @Test
    public void VerifyPasswordFailedTest() {
        String passwordHash = securityService.HashPassword("abc");
        boolean success = securityService.Verify("123", passwordHash);
        Assert.assertFalse(success);
    }
}
