# Поиск подстроки (Алгоритм Бойера — Мура — Хорспула)

## Сборка

```
mvn package
```

## Запуск

В ```<project root>/target``` запустить ```grep-enhanced-1.0-SNAPSHOT-jar-with-dependencies```:
```
java -jar prog.jar <option>
Options:
-h                      print help
--data <path> <pattern> find text pattern in specified file or directory including subdirectories
--name <path> <pattern> find text pattern in file names in specified directory including subdirectories
```
