// ============================================================================
// SPR プロジェクト用 Controller テストテンプレート
// ============================================================================
// このファイルは Controller テストを作成する際のテンプレートです。
// 実際のテストファイル作成時は、パッケージ名、クラス名、フィールドを適切に変更してください。
// ============================================================================

package com.spr.presentation;

import com.spr.application.dto.{DTO_CLASS_NAME};
import com.spr.application.usecase.{USE_CASE_CLASS_NAME};
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * {CONTROLLER_CLASS_NAME} のテストクラス
 *
 * テスト対象：{CONTROLLER_CLASS_NAME}
 * 依存関係：{USE_CASE_CLASS_NAME}
 * エンドポイント：{API_ENDPOINTS}
 */
@WebMvcTest({CONTROLLER_CLASS_NAME}.class)
class {CONTROLLER_CLASS_NAME}Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private {USE_CASE_CLASS_NAME} {usecase_field_name};

    @Autowired
    private ObjectMapper objectMapper;

    // ============================================================================
    // GET エンドポイント（単体取得）のテストパターン
    // ============================================================================

    @Test
    void get{Entity}_正常な{ENTITY}ID_{Entity}を返す() throws Exception {
        // Arrange
        {ID_TYPE} {entity}Id = {SAMPLE_ID};
        {DTO_CLASS_NAME} expected{Entity}Dto = {SAMPLE_DTO_INSTANCE};
        when({usecase_field_name}.execute({entity}Id)).thenReturn(expected{Entity}Dto);

        // Act & Assert
        mockMvc.perform(get("{API_BASE_PATH}/{{{entity}_id}}", {entity}Id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.{ID_FIELD}").value({EXPECTED_ID}))
                .andExpect(jsonPath("$.{FIELD_1}").value({EXPECTED_VALUE_1}))
                .andExpect(jsonPath("$.{FIELD_2}").value({EXPECTED_VALUE_2}));

        // Verify
        verify({usecase_field_name}).execute({entity}Id);
    }

    @Test
    void get{Entity}_異なる{ENTITY}ID_対応する{Entity}を返す() throws Exception {
        // Arrange
        {ID_TYPE} {entity}Id = {DIFFERENT_SAMPLE_ID};
        {DTO_CLASS_NAME} expected{Entity}Dto = {DIFFERENT_SAMPLE_DTO_INSTANCE};
        when({usecase_field_name}.execute({entity}Id)).thenReturn(expected{Entity}Dto);

        // Act & Assert
        mockMvc.perform(get("{API_BASE_PATH}/{{{entity}_id}}", {entity}Id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.{ID_FIELD}").value({DIFFERENT_EXPECTED_ID}))
                .andExpect(jsonPath("$.{FIELD_1}").value({DIFFERENT_EXPECTED_VALUE_1}))
                .andExpect(jsonPath("$.{FIELD_2}").value({DIFFERENT_EXPECTED_VALUE_2}));

        // Verify
        verify({usecase_field_name}).execute({entity}Id);
    }

    // ============================================================================
    // GET エンドポイント（リスト取得）のテストパターン
    // ============================================================================

    @Test
    void get{Entities}_正常なuserID_{Entity}リストを返す() throws Exception {
        // Arrange
        Integer userId = {SAMPLE_USER_ID};
        List<{DTO_CLASS_NAME}> expected{Entity}List = Arrays.asList(
            {SAMPLE_DTO_INSTANCE_1},
            {SAMPLE_DTO_INSTANCE_2}
        );
        when({usecase_field_name}.execute(userId)).thenReturn(expected{Entity}List);

        // Act & Assert
        mockMvc.perform(get("{API_BASE_PATH}?user_id={userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.{RESPONSE_WRAPPER_FIELD}").isArray())
                .andExpected(jsonPath("$.{RESPONSE_WRAPPER_FIELD}").value(hasSize(2)))
                .andExpected(jsonPath("$.{RESPONSE_WRAPPER_FIELD}[0].{ID_FIELD}").value({EXPECTED_FIRST_ID}))
                .andExpected(jsonPath("$.{RESPONSE_WRAPPER_FIELD}[1].{ID_FIELD}").value({EXPECTED_SECOND_ID}));

        // Verify
        verify({usecase_field_name}).execute(userId);
    }

    @Test
    void get{Entities}_空のリスト_空の配列を返す() throws Exception {
        // Arrange
        Integer userId = {SAMPLE_USER_ID};
        List<{DTO_CLASS_NAME}> empty{Entity}List = Arrays.asList();
        when({usecase_field_name}.execute(userId)).thenReturn(empty{Entity}List);

        // Act & Assert
        mockMvc.perform(get("{API_BASE_PATH}?user_id={userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpected(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpected(jsonPath("$.{RESPONSE_WRAPPER_FIELD}").isArray())
                .andExpected(jsonPath("$.{RESPONSE_WRAPPER_FIELD}").isEmpty());

        // Verify
        verify({usecase_field_name}).execute(userId);
    }

    // ============================================================================
    // 例外処理テストパターン
    // ============================================================================

    @Test
    void get{Entity}_UseCaseが例外をスロー_例外が伝播される() throws Exception {
        // Arrange
        {ID_TYPE} {entity}Id = {INVALID_ID};
        when({usecase_field_name}.execute({entity}Id)).thenThrow(new RuntimeException("{Entity} not found"));

        // Act & Assert
        // Spring Boot Test では未処理の例外は通常 ServletException としてラップされる
        try {
            mockMvc.perform(get("{API_BASE_PATH}/{{{entity}_id}}", {entity}Id)
                            .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            // ServletException が発生することを確認
            assert e.getCause() instanceof RuntimeException;
            assert "{Entity} not found".equals(e.getCause().getMessage());
        }

        // Verify
        verify({usecase_field_name}).execute({entity}Id);
    }

    @Test
    void get{Entity}_無効なパスパラメータ_バッドリクエストを返す() throws Exception {
        // Act & Assert
        mockMvc.perform(get("{API_BASE_PATH}/{{{entity}_id}}", "invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpected(status().isBadRequest());
    }

    @Test
    void get{Entities}_必須クエリパラメータなし_バッドリクエストを返す() throws Exception {
        // Act & Assert
        mockMvc.perform(get("{API_BASE_PATH}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // ============================================================================
    // モック検証テストパターン
    // ============================================================================

    @Test
    void get{Entity}_UseCaseが正しく呼び出される_モックが検証される() throws Exception {
        // Arrange
        {ID_TYPE} {entity}Id = {MOCK_VERIFICATION_ID};
        {DTO_CLASS_NAME} {entity}Dto = {MOCK_DTO_INSTANCE};
        when({usecase_field_name}.execute(any({ID_TYPE}.class))).thenReturn({entity}Dto);

        // Act
        mockMvc.perform(get("{API_BASE_PATH}/{{{entity}_id}}", {entity}Id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpected(status().isOk());

        // Assert
        verify({usecase_field_name}).execute({entity}Id);
    }
}

// ============================================================================
// テンプレート置換変数説明
// ============================================================================
/*
{CONTROLLER_CLASS_NAME}     - テスト対象のControllerクラス名 (例: CommonApiController)
{USE_CASE_CLASS_NAME}       - 依存するUseCaseクラス名 (例: GetTaskUseCase)
{DTO_CLASS_NAME}            - DTOクラス名 (例: TaskDto, GoodDto)
{usecase_field_name}        - UseCaseのフィールド名 (例: getTaskUseCase, getGoodsUseCase)
{Entity}                    - エンティティ名（単数形、大文字開始） (例: Task, Good)
{Entities}                  - エンティティ名（複数形、大文字開始） (例: Tasks, Goods)
{ENTITY}                    - エンティティ名（大文字） (例: TASK, GOOD)
{entity}                    - エンティティ名（小文字） (例: task, good)
{ID_TYPE}                   - IDの型 (例: Integer, Long, String)
{API_BASE_PATH}             - APIのベースパス (例: /v1/anv/common/tasks)
{API_ENDPOINTS}             - 実装されているエンドポイント一覧
{ID_FIELD}                  - JSONレスポンスのIDフィールド名 (例: id, taskId, goodId)
{FIELD_1}, {FIELD_2}        - その他の検証対象フィールド
{RESPONSE_WRAPPER_FIELD}    - リストレスポンスのラッパーフィールド (例: tasks, goods)

サンプル値変数:
{SAMPLE_ID}                 - テスト用のサンプルID
{SAMPLE_DTO_INSTANCE}       - テスト用のDTOインスタンス
{EXPECTED_ID}               - 期待されるID値
{EXPECTED_VALUE_1}          - 期待される値1
等...
*/