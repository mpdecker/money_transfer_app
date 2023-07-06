package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class JdbcAccountDaoTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    private JdbcAccountDao accountDao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        accountDao = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void getBalanceReturnsForExistingUser() {
        int userId = 123;
        BigDecimal expectedBalance = new BigDecimal("1000.00");

        String sql = "SELECT balance FROM account JOIN tenmo_user ON account.user_id = tenmo_user.user_id WHERE account.user_id = ?;";
        SqlRowSet rowSet = mock(SqlRowSet.class);
        when(rowSet.next()).thenReturn(true);
        when(rowSet.getBigDecimal("balance")).thenReturn(expectedBalance);
        when(jdbcTemplate.queryForRowSet(sql, userId)).thenReturn(rowSet);

        BigDecimal balance = accountDao.getBalance(userId);

        assertEquals(expectedBalance, balance);
        verify(jdbcTemplate).queryForRowSet(sql, userId);
    }

}
