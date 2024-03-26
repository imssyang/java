# java

```bash
ln -s /opt/java/jenv .jenv
jenv add "$(/usr/libexec/java_home)"
jenv add "$(/usr/libexec/java_home -v 17)"
jenv versions
jenv global 17.0.10
jenv local 17.0.10   # For current directory by ".java-version"
jenv shell oracle64-17.0.10
jenv doctor
```

