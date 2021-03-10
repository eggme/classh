package me.eggme.classh.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Delegate;
import me.eggme.classh.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@Getter
public class UserDetailsVO implements UserDetails {

    @Delegate
    private Member member;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return member.isEnable();
    }

    @Override
    public boolean isAccountNonLocked() {
        return member.isEnable();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return member.isEnable();
    }

    @Override
    public boolean isEnabled() {
        return member.isEnable();
    }
}
