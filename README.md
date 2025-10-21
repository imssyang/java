# jenv

```bash
# Ignore untracked content of jenv
git config -f .gitmodules submodule.jenv.ignore untracked

# Soft-link to submodule
ln -s /opt/java/jenv .jenv
```

# java

```bash
# linux
jenv add /opt/java/jdk/21.0.8
jenv enable-plugin export      # set JAVA_HOME
jenv global 21

# Macos
jenv add "$(/usr/libexec/java_home)"        # install by xxx.dmg on macos
jenv add "$(/usr/libexec/java_home -v 17)"  # install by xxx.dmg on macos
jenv add "/usr/lib/jvm/jdk-17-oracle-x64"   # install by jdk-17_linux-x64_bin.deb
jenv enable-plugin export                   # set JAVA_HOME
jenv global 17.0.10
jenv local 17.0.10   # For current directory by ".java-version"
jenv shell oracle64-17.0.10

# Check
jenv versions
jenv doctor
```

