# Mutation Testing Report — PIT (PITest)

**Projeto:** dslearn  
**Data:** 2026-04-03  
**PIT version:** 1.19.6  
**Mutators:** DEFAULTS  
**Escopo:** `com.restful.dslearn.service.*`

---

## Resumo

| Metrica                         | Valor         |
|---------------------------------|---------------|
| Line Coverage (classes mutadas) | 98% (556/565) |
| Mutacoes geradas                | 290           |
| Mutacoes mortas (KILLED)        | 170 (59%)     |
| Mutacoes sobreviventes          | 72            |
| Sem cobertura (NO_COVERAGE)     | 48            |
| Test strength                   | 70%           |

---

## Resultado por Service

| Classe              | Total   | Killed  | Survived | No Coverage | Score   |
|---------------------|---------|---------|----------|-------------|---------|
| ContentService      | 21      | 13      | 6        | 2           | 61%     |
| CourseService       | 15      | 13      | 1        | 1           | 86%     |
| DeliverService      | 30      | 14      | 9        | 7           | 46%     |
| EnrollmentService   | 25      | 16      | 3        | 6           | 64%     |
| NotificationService | 19      | 13      | 4        | 2           | 68%     |
| OfferService        | 19      | 12      | 5        | 2           | 63%     |
| ReplyService        | 19      | 12      | 2        | 5           | 63%     |
| ResourceService     | 23      | 13      | 8        | 2           | 56%     |
| RoleService         | 14      | 11      | 2        | 1           | 78%     |
| SectionService      | 29      | 14      | 9        | 6           | 48%     |
| TaskService         | 27      | 12      | 12       | 3           | 44%     |
| TopicService        | 33      | 16      | 7        | 10          | 48%     |
| UserService         | 16      | 11      | 4        | 1           | 68%     |
| **TOTAL**           | **290** | **170** | **72**   | **48**      | **59%** |

---

## Mutacoes Sobreviventes (detalhe)

As mutacoes listadas abaixo sobreviveram — indicam pontos onde os testes executam o codigo mas nao verificam o efeito do
setter/chamada removido.

### ContentService (6 sobreviventes)

| Metodo | Linha | Mutacao                                |
|--------|-------|----------------------------------------|
| create | L46   | removed call to `Content::setPosition` |
| create | L48   | removed call to `Content::setText`     |
| create | L49   | removed call to `Content::setVideoUri` |
| update | L60   | removed call to `Content::setPosition` |
| update | L61   | removed call to `Content::setSection`  |
| update | L62   | removed call to `Content::setText`     |

### CourseService (1 sobrevivente)

| Metodo | Linha | Mutacao                                 |
|--------|-------|-----------------------------------------|
| update | L57   | removed call to `Course::setImgGrayUri` |

### DeliverService (9 sobreviventes)

| Metodo            | Linha | Mutacao                                    |
|-------------------|-------|--------------------------------------------|
| create            | L65   | removed call to `Deliver::setUri`          |
| create            | L66   | removed call to `Deliver::setFeedback`     |
| create            | L67   | removed call to `Deliver::setCorrectCount` |
| update            | L83   | removed call to `Deliver::setUri`          |
| update            | L85   | removed call to `Deliver::setCorrectCount` |
| update            | L87   | removed call to `Deliver::setLesson`       |
| update            | L88   | removed call to `Deliver::setEnrollment`   |
| buildEnrollmentId | L119  | removed call to `EnrollmentPK::setUser`    |
| buildEnrollmentId | L120  | removed call to `EnrollmentPK::setOffer`   |

### EnrollmentService (3 sobreviventes)

| Metodo  | Linha | Mutacao                                  |
|---------|-------|------------------------------------------|
| buildId | L105  | removed call to `EnrollmentPK::setUser`  |
| buildId | L106  | removed call to `EnrollmentPK::setOffer` |
| delete  | L79   | replaced equality check with false       |

### NotificationService (4 sobreviventes)

| Metodo | Linha | Mutacao                                    |
|--------|-------|--------------------------------------------|
| create | L47   | removed call to `Notification::setReading` |
| create | L48   | removed call to `Notification::setRoute`   |
| update | L61   | removed call to `Notification::setRoute`   |
| update | L62   | removed call to `Notification::setUser`    |

### OfferService (5 sobreviventes)

| Metodo | Linha | Mutacao                                 |
|--------|-------|-----------------------------------------|
| create | L47   | removed call to `Offer::setStartMoment` |
| create | L48   | removed call to `Offer::setEndMoment`   |
| update | L61   | removed call to `Offer::setStartMoment` |
| update | L62   | removed call to `Offer::setEndMoment`   |
| update | L63   | removed call to `Offer::setCourse`      |

### ReplyService (2 sobreviventes)

| Metodo | Linha | Mutacao                            |
|--------|-------|------------------------------------|
| update | L68   | removed call to `Reply::setTopic`  |
| update | L69   | removed call to `Reply::setAuthor` |

### ResourceService (8 sobreviventes)

| Metodo | Linha | Mutacao                                    |
|--------|-------|--------------------------------------------|
| create | L46   | removed call to `Resource::setDescription` |
| create | L47   | removed call to `Resource::setPosition`    |
| create | L48   | removed call to `Resource::setImgUri`      |
| create | L49   | removed call to `Resource::setType`        |
| update | L62   | removed call to `Resource::setDescription` |
| update | L63   | removed call to `Resource::setPosition`    |
| update | L64   | removed call to `Resource::setImgUri`      |
| update | L66   | removed call to `Resource::setOffer`       |

### RoleService (2 sobreviventes)

| Metodo | Linha | Mutacao                          |
|--------|-------|----------------------------------|
| create | L45   | removed call to `Role::setUsers` |
| update | L56   | removed call to `Role::setUsers` |

### SectionService (9 sobreviventes)

| Metodo | Linha | Mutacao                                   |
|--------|-------|-------------------------------------------|
| create | L46   | removed call to `Section::setTitle`       |
| create | L47   | removed call to `Section::setDescription` |
| create | L48   | removed call to `Section::setPosition`    |
| create | L49   | removed call to `Section::setImgUri`      |
| update | L69   | removed call to `Section::setDescription` |
| update | L70   | removed call to `Section::setPosition`    |
| update | L71   | removed call to `Section::setImgUri`      |
| update | L72   | removed call to `Section::setResource`    |
| update | L74   | replaced equality check with false        |

### TaskService (12 sobreviventes)

| Metodo | Linha | Mutacao                                  |
|--------|-------|------------------------------------------|
| create | L45   | removed call to `Task::setTitle`         |
| create | L46   | removed call to `Task::setPosition`      |
| create | L49   | removed call to `Task::setQuestionCount` |
| create | L50   | removed call to `Task::setApprovalCount` |
| create | L51   | removed call to `Task::setWeight`        |
| create | L52   | removed call to `Task::setDueDate`       |
| update | L63   | removed call to `Task::setPosition`      |
| update | L64   | removed call to `Task::setSection`       |
| update | L65   | removed call to `Task::setDescription`   |
| update | L66   | removed call to `Task::setQuestionCount` |
| update | L67   | removed call to `Task::setApprovalCount` |
| update | L69   | removed call to `Task::setDueDate`       |

### TopicService (7 sobreviventes)

| Metodo | Linha | Mutacao                            |
|--------|-------|------------------------------------|
| create | L66   | removed call to `Topic::setTitle`  |
| create | L67   | removed call to `Topic::setBody`   |
| update | L93   | removed call to `Topic::setBody`   |
| update | L94   | removed call to `Topic::setAuthor` |
| update | L95   | removed call to `Topic::setOffer`  |
| update | L96   | removed call to `Topic::setLesson` |
| update | L98   | replaced equality check with false |

### UserService (4 sobreviventes)

| Metodo | Linha | Mutacao                             |
|--------|-------|-------------------------------------|
| create | L45   | removed call to `User::setEmail`    |
| create | L46   | removed call to `User::setPassword` |
| update | L58   | removed call to `User::setPassword` |
| update | L59   | removed call to `Set::clear`        |

---

## Analise

O padrao dominante de mutacoes sobreviventes e **`removed call to Entity::setXxx`** nos metodos `create()` e `update()`.
Isso acontece porque os testes verificam apenas alguns campos do DTO retornado (ex: `id`, `name`) mas nao todos. Quando
o PIT remove um `setPosition()`, o campo fica `null` no DTO, mas o teste nao verifica `position`, entao a mutacao
sobrevive.

### Acoes para aumentar o mutation score

1. **Verificar todos os campos do DTO** nas assercoes de `create` e `update` — usar `extracting()` do AssertJ com todos
   os campos
2. **Testar campos opcionais** como `imgUri`, `description`, `startMoment`, `endMoment`
3. **Testar o branch `else`** de condicionais como `prerequisiteId != null` e `answerId != null` (3 mutacoes de
   `replaced equality check with false`)
4. **Testar `buildId`/`buildEnrollmentId`** indiretamente verificando que a busca correta foi feita

---

## Como reproduzir

```bash
./mvnw org.pitest:pitest-maven:mutationCoverage
```

Relatorio HTML: `docs/tests/mutation/index.html`  
Relatorio XML: `docs/tests/mutation/mutations.xml`
