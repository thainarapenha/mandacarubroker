# Desafio 1 - Módulo Jandaia | Relatório de Qualidade de Software

## Introdução

Esta documentação descreve as alterações feitas no projeto mandacaru broker. O objetivo é aplicar conhecimentos de
qualidade de sóftware vistos no módulo Jandaia. O mesmo possui novas funcionalidades e correções no código do projeto.

## Alterações Realizadas

1. Inclusão de novas dependências:
   - Checkstyle 
   - MySQL
2. Correção no caminho de importações
    - Remoção de * e o substituindo por propriedades específicas usadas no projeto
3. Correção de bugs 
   - Criação de novas stocks,
   - Atualização de uma stock existente;
   - Correção no padrão de escrita no código da ação
4. Adição de testes
   - Testes voltados para Controller do projeto;


## 1.1 Inclusão do Checkstyle 

Sua dependência foi adicionada ao 'arquivo pom.xml' como mostrado abaixo:
```XML
  <dependency>
    <groupId>com.puppycrawl.tools</groupId>
    <artifactId>checkstyle</artifactId>
    <version>10.13.0</version>
  </dependency>
```
E também foi adicionado um arquivo de configuração do mesmo no seguinte caminho: config/checkstyle.xml
As configurações usadas no arquivo abaixo foram feitas seguindo as práticas de linting e utilizando o [Checkstyle](https://checkstyle.sourceforge.io/) como ferramenta.

````XML
<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
        "-//Checkstyle//DTD Checkstyle Configuration 1.3//EN"
        "https://checkstyle.org/dtds/configuration_1_3.dtd">

<!--
  Checkstyle configuration that checks the sun coding conventions from:

    - the Java Language Specification at
      https://docs.oracle.com/javase/specs/jls/se11/html/index.html

    - the Sun Code Conventions at https://www.oracle.com/java/technologies/javase/codeconventions-contents.html

    - the Javadoc guidelines at
      https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html

    - the JDK Api documentation https://docs.oracle.com/en/java/javase/11/

    - some best practices

  Checkstyle is very configurable. Be sure to read the documentation at
  https://checkstyle.org (or in your downloaded distribution).

  Most Checks are configurable, be sure to consult the documentation.

  To completely disable a check, just comment it out or delete it from the file.
  To suppress certain violations please review suppression filters.

  Finally, it is worth reading the documentation.

-->

<module name="Checker">
    <!--
        If you set the basedir property below, then all reported file
        names will be relative to the specified directory. See
        https://checkstyle.org/config.html#Checker

        <property name="basedir" value="${basedir}"/>
    -->
    <property name="severity" value="error"/>

    <property name="fileExtensions" value="java, properties, xml"/>

    <!-- Excludes all 'module-info.java' files              -->
    <!-- See https://checkstyle.org/filefilters/index.html -->
    <module name="BeforeExecutionExclusionFileFilter">
        <property name="fileNamePattern" value="module\-info\.java$"/>
    </module>

    <!-- https://checkstyle.org/filters/suppressionfilter.html -->
    <module name="SuppressionFilter">
        <property name="file" value="${org.checkstyle.sun.suppressionfilter.config}"
                  default="checkstyle-suppressions.xml" />
        <property name="optional" value="true"/>
    </module>

    <!-- Checks whether files end with a new line.                        -->
    <!-- See https://checkstyle.org/checks/misc/newlineatendoffile.html -->
    <module name="NewlineAtEndOfFile"/>

    <!-- Checks that property files contain the same keys.         -->
    <!-- See https://checkstyle.org/checks/misc/translation.html -->
    <module name="Translation"/>

    <!-- Checks for whitespace                               -->
    <!-- See https://checkstyle.org/checks/whitespace/index.html -->
    <module name="FileTabCharacter"/>

    <!-- Miscellaneous other checks.                   -->
    <!-- See https://checkstyle.org/checks/misc/index.html -->
    <module name="RegexpSingleline">
        <property name="format" value="\s+$"/>
        <property name="minimum" value="0"/>
        <property name="maximum" value="0"/>
        <property name="message" value="Line has trailing spaces."/>
    </module>

    <!-- Checks for Headers                                -->
    <!-- See https://checkstyle.org/checks/header/index.html   -->
    <!-- <module name="Header"> -->
    <!--   <property name="headerFile" value="${checkstyle.header.file}"/> -->
    <!--   <property name="fileExtensions" value="java"/> -->
    <!-- </module> -->

    <module name="TreeWalker">

        <!-- Checks for Javadoc comments.                     -->
        <!-- See https://checkstyle.org/checks/javadoc/index.html -->
        <module name="InvalidJavadocPosition"/>
        <module name="JavadocMethod"/>
        <module name="JavadocType"/>
        <module name="JavadocVariable"/>
        <module name="JavadocStyle"/>
        <module name="MissingJavadocMethod"/>

        <!-- Checks for Naming Conventions.                  -->
        <!-- See https://checkstyle.org/checks/naming/index.html -->
        <module name="ConstantName"/>
        <module name="LocalFinalVariableName"/>
        <module name="LocalVariableName"/>
        <module name="MemberName"/>
        <module name="MethodName"/>
        <module name="PackageName"/>
        <module name="ParameterName"/>
        <module name="StaticVariableName"/>
        <module name="TypeName"/>

        <module name="AbbreviationAsWordInName"/>

        <!-- Checks for imports                              -->
        <!-- See https://checkstyle.org/checks/imports/index.html -->
        <module name="AvoidStarImport"/>
        <module name="IllegalImport"/> <!-- defaults to sun.* packages -->
        <module name="RedundantImport"/>
        <module name="UnusedImports">
            <property name="processJavadoc" value="false"/>
        </module>

        <!-- Checks for Size Violations.                    -->
        <!-- See https://checkstyle.org/checks/sizes/index.html -->
        <module name="MethodLength"/>
        <module name="ParameterNumber"/>

        <!-- Checks for whitespace                               -->
        <!-- See https://checkstyle.org/checks/whitespace/index.html -->
        <module name="EmptyForIteratorPad"/>
        <module name="GenericWhitespace"/>
        <module name="MethodParamPad"/>
        <module name="NoWhitespaceAfter"/>
        <module name="NoWhitespaceBefore"/>
        <module name="OperatorWrap"/>
        <module name="ParenPad"/>
        <module name="TypecastParenPad"/>
        <module name="WhitespaceAround"/>

        <!-- Modifier Checks                                    -->
        <!-- See https://checkstyle.org/checks/modifier/index.html -->
        <module name="ModifierOrder"/>
        <module name="RedundantModifier"/>

        <!-- Checks for blocks. You know, those {}'s         -->
        <!-- See https://checkstyle.org/checks/blocks/index.html -->
        <module name="AvoidNestedBlocks"/>
        <module name="EmptyBlock"/>
        <module name="LeftCurly"/>
        <module name="NeedBraces"/>
        <module name="RightCurly"/>

        <!-- Checks for common coding problems               -->
        <!-- See https://checkstyle.org/checks/coding/index.html -->
        <module name="EmptyStatement"/>
        <module name="EqualsHashCode"/>
        <module name="HiddenField"/>
        <module name="IllegalInstantiation"/>
        <module name="InnerAssignment"/>
        <module name="MagicNumber"/>
        <module name="MissingSwitchDefault"/>
        <module name="MultipleVariableDeclarations"/>
        <module name="SimplifyBooleanExpression"/>
        <module name="SimplifyBooleanReturn"/>

        <!-- Checks for class design                         -->
        <!-- See https://checkstyle.org/checks/design/index.html -->
        <module name="DesignForExtension"/>
        <module name="FinalClass"/>
        <module name="HideUtilityClassConstructor"/>
        <module name="InterfaceIsType"/>
        <module name="VisibilityModifier"/>

        <!-- Miscellaneous other checks.                   -->
        <!-- See https://checkstyle.org/checks/misc/index.html -->
        <module name="ArrayTypeStyle"/>
        <module name="FinalParameters"/>
        <module name="TodoComment"/>
        <module name="UpperEll"/>

        <!-- https://checkstyle.org/filters/suppressionxpathfilter.html -->
        <module name="SuppressionXpathFilter">
            <property name="file" value="${org.checkstyle.sun.suppressionxpathfilter.config}"
                      default="checkstyle-xpath-suppressions.xml" />
            <property name="optional" value="true"/>
        </module>

    </module>

</module>
````
## 1.2 Inclusão do MySQL

Para que fosse possível a utilização do projeto, foi adicionado uma configuração
de um banco MySQL a fim de realizar seus respectivos testes e verificações.

````XML
  <dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-mysql</artifactId>
  </dependency>
  <dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
  </dependency>
  <dependency>
      <groupId>com.mysql</groupId>
      <artifactId>mysql-connector-j</artifactId>
      <version>8.3.0</version>
  </dependency>
````
## 2 Correção no caminho de importações

Seguindo os princípios de boas práticas, foram alterados em todo o projeto os asteriscos (*) das importações os substituindo por 
propriedades específicas usadas no código. Abaixo segue um exemplo que que foi alterado em todo o projeto:

Importação antes
````java
import org.springframework.web.bind.annotation.*;
````

Como passou a ser
````java
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
````

## 3 Correção de bugs

Alguns métodos foram alterados a fim de diminuir sua complexidade, além de fornecer melhores retornos. Os códigos abaixos são referentes ao arquivo StockController.java

````java
    /**
     * Retorna uma ação pelo ID.
     * @return ResponseEntity contendo o objeto Stock se encontrado e o código de status respectivamente.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable String id) {
        Stock stock = stockService.getStockById(id).orElse(null);
        return stock != null ? ResponseEntity.ok(stock) : ResponseEntity.notFound().build();
    }

    /**
     * Cria uma nova ação com base nos dados fornecidos.
     * @return ResponseEntity contendo a ação criada e código de status HTTP apropriado.
     */
    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody RequestStockDTO data) {
        Stock createdStock = stockService.createStock(data);
        return ResponseEntity.ok(createdStock);
    }

    /**
     * Atualiza uma ação existente com base no ID.
     * @return ResponseEntity contendo o objeto Stock atualizado e o código de status respectivamente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable String id, @RequestBody Stock updatedStock) {
        try {
            Stock result = stockService.updateStock(id, updatedStock);

            if (result != null) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
````

Visto essas alterações, se fez necessário também ajustar o arquivo StockService.java mais especificamente no updateStock resposável por atualizar uma ação.

````java
public Stock updateStock(String id, Stock updatedStock) {
  Optional<Stock> optionalStock = stockRepository.findById(id);

  if (optionalStock.isPresent()) {
      Stock stock = optionalStock.get();
      stock.setSymbol(updatedStock.getSymbol());
      stock.setCompanyName(updatedStock.getCompanyName());
      stock.setPrice(updatedStock.getPrice());

      return stockRepository.save(stock);
  } else {
      return null;
  }
}
````

Outro detalhe importante destas alterações está no arquivo Stock na pasta domain, onde o código em questão teve sua correção e complexidade reduzida como mostrado logo abaixo:

````java
    public Stock(RequestStockDTO requestStockDTO) {
        this.symbol = requestStockDTO.symbol();
        this.companyName = requestStockDTO.companyName();
        this.price = requestStockDTO.price();
    }

    public double changePrice(double newPrice) {
        return newPrice;
    }
````

Por fim, o numero de letras e números foi corrigido para o que é esperado de uma ação, como por exemplo: MGLU3.

````java
  @Pattern(regexp = "[A-Za-z]{4}[0-9]{1}", message = "Symbol must be 4 letters followed by 1 number")
  String symbol,
````

## 4. Adição de testes

A fim de garantir a qualidade do software, bem como sua integridade e boas práticas, foram in=mplementados alguns testes de código ao projeto. Os mesmos buscaram abranger o sucesso de uma ação retornando status 200, mensagem de aviso em caso de não encontrarem algum Id, dentre outras validações.

````java
@SpringBootTest
@AutoConfigureMockMvc
class ControllerTests {

    // Injeção de dependências necessárias para os testes
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Método executado antes de cada teste para inicializar o repositório com dados de exemplo
     */
    @BeforeEach
    public void initRepository() {
        stockRepository.save(new Stock(new RequestStockDTO("MGLU3", "Magazine Luiza", 2.04)));
        stockRepository.save(new Stock(new RequestStockDTO("ABEV3", "Ambev", 12.76)));
        stockRepository.save(new Stock(new RequestStockDTO("ITSA4", "Itaúsa", 10.66)));
    }

    /**
     * Limpa repositório ao final de cada teste
     */
    @AfterEach
    public void clearRepository() {
        stockRepository.deleteAll();
    }

    /**
     * Testa se a requisição para obter todas as ações retorna um status HTTP OK (200)
     */
    @Test
    void checkStatusGetAllStocks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/stocks"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Testa se a requisição para obter uma ação por ID inexistente retorna um status HTTP Not Found (404)
     */
    @Test
    void checksIfGetIdStocksExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/stocks/{id}", "id não existe"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Testa se a criação de uma nova ação retorna um status HTTP OK (200) e verifica os detalhes da ação criada
     */
    @Test
    void checkIfCreatedNewStock() throws Exception {
        RequestStockDTO newStock = new RequestStockDTO("MGLU3", "Magazine Luiza", 2.04);

        String requestJson = objectMapper.writeValueAsString(newStock);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/stocks")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(jsonPath("$.symbol").value(newStock.symbol()))
                .andExpect(jsonPath("$.companyName").value(newStock.companyName()))
                .andExpect(jsonPath("$.price").value(newStock.price()));

    }

    /**
     * Testa se a atualização de uma ação retorna um status HTTP OK (200) e verifica os detalhes da ação atualizada
     */
    @Test
    void checkUpdateStockById() throws Exception {
        Stock targetUpdatingStock = stockRepository.findAll().get(0);

        targetUpdatingStock.setSymbol("MGLU5");

        String requestJson = objectMapper.writeValueAsString(targetUpdatingStock);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/stocks/{id}", targetUpdatingStock.getId())
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(jsonPath("$.symbol").value(targetUpdatingStock.getSymbol()))
                .andExpect(jsonPath("$.companyName").value(targetUpdatingStock.getCompanyName()))
                .andExpect(jsonPath("$.price").value(targetUpdatingStock.getPrice()));

    }

    /**
     * Testa se a exclusão de uma ação por ID retorna um status HTTP OK (200)
     */
    @Test
    void checkDeleteStockById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/stocks/{id}", "6c0c7a08-8313-45db-97ef-236d29bf940b"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Testa se a exclusão de uma ação com ID inexistente retorna um status HTTP No Content (204)
     */
    @Test
    void checksIfDeleteWithNotExistentId() throws Exception  {
        String nonexistentId = "1a2b3c4d5e7f8g9h";

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/stocks/{id}", nonexistentId);

        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }

    /**
     * Testa se a tentativa de excluir todas as ações retorna um status HTTP Method Not Allowed (405)
     */
    @Test
    void canNotDeleteAllStocks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/stocks"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }
}
````
