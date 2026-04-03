# Relatório de Cobertura JaCoCo

## Execução

- **Data/Hora:** 2026-04-03T13:12:21-03:00
- **Comando executado:** `./mvnw test -DskipITs -q`
- **Status:** Falhou na fase de testes com erro de inicialização do Mockito (ver seção abaixo).
- **Perfil de JVM observado:** Java 21.0.10 (OpenJDK 64-Bit Server VM), conforme log do teste.

## Resultado do Maven/Surefire

- **Tests run:** 149
- **Failures:** 0
- **Errors:** 149
- **Skipped:** 0

Falha principal:

- `IllegalStateException: Could not initialize plugin: interface org.mockito.plugins.MockMaker`
- Causa raiz: `Mockito` não conseguiu carregar o *inline byte-buddy mock maker* porque o mecanismo de self-attach do JVM
  falhou.

## Cobertura Total (JaCoCo)

Métricas extraídas de `target/site/jacoco/jacoco.csv`:

- **Instructions:** 3.030 / 4.466 (67.85%)
- **Branches:** 41 / 266 (15.41%)
- **Lines:** 694 / 943 (73.59%)
- **Complexity:** 203 / 429 (47.32%)
- **Methods:** 185 / 296 (62.50%)

## Cobertura por pacote

| Pacote                           | Instructions | Branches |   Lines | Complexity | Methods |
|----------------------------------|-------------:|---------:|--------:|-----------:|--------:|
| `com.restful.dslearn`            |       37.50% |    0.00% |  33.33% |     50.00% |  50.00% |
| `com.restful.dslearn.controller` |       52.01% |    0.00% |  57.63% |     51.28% |  51.28% |
| `com.restful.dslearn.dto`        |      100.00% |    0.00% | 100.00% |    100.00% | 100.00% |
| `com.restful.dslearn.entity`     |       14.50% |    0.89% |  18.61% |      5.48% |  23.53% |
| `com.restful.dslearn.service`    |       87.40% |   92.86% |  98.41% |     72.32% |  70.51% |

## Artefatos

- Relatório HTML: [target/site/jacoco/index.html](../../../target/site/jacoco/index.html)
- Relatório CSV: `target/site/jacoco/jacoco.csv`
- Relatórios de teste: `target/surefire-reports/`
