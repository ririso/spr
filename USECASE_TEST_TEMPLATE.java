// ============================================================================
// SPR プロジェクト用 UseCase テストテンプレート
// ============================================================================
// このファイルは UseCase テストを作成する際のテンプレートです。
// 実際のテストファイル作成時は、パッケージ名、クラス名、フィールドを適切に変更してください。
// ============================================================================

package com.spr.application.usecase;

import com.spr.application.dto.{DTO_CLASS_NAME};
import com.spr.infrastructure.{DATA_SOURCE_INTERFACE};
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {USE_CASE_CLASS_NAME} のテストクラス
 *
 * テスト対象：{USE_CASE_CLASS_NAME}
 * 依存関係：{DATA_SOURCE_INTERFACE}
 * ビジネスロジック：{BUSINESS_LOGIC_DESCRIPTION}
 */
@ExtendWith(MockitoExtension.class)
class {USE_CASE_CLASS_NAME}Test {

    @Mock
    private {DATA_SOURCE_INTERFACE} {dataSourceFieldName};

    @InjectMocks
    private {USE_CASE_CLASS_NAME} {useCaseFieldName};

    // ============================================================================
    // 単体取得パターンのテスト
    // ============================================================================

    @Test
    void execute_正常な{ENTITY}ID_{Entity}を返す() {
        // Arrange
        final var {entity}Id = {SAMPLE_ID};
        final var expected{Entity}Dto = {SAMPLE_DTO_INSTANCE};
        when({dataSourceFieldName}.get{Entity}({entity}Id)).thenReturn(expected{Entity}Dto);

        // Act
        final var actual = {useCaseFieldName}.execute({entity}Id);

        // Assert
        assertThat(actual).isEqualTo(expected{Entity}Dto);
        assertThat(actual.{ID_FIELD}()).isEqualTo({EXPECTED_ID});
        assertThat(actual.{FIELD_1}()).isEqualTo({EXPECTED_VALUE_1});
        assertThat(actual.{FIELD_2}()).isEqualTo({EXPECTED_VALUE_2});

        // Verify
        verify({dataSourceFieldName}).get{Entity}({entity}Id);
    }

    @Test
    void execute_異なる{ENTITY}ID_対応する{Entity}を返す() {
        // Arrange
        final var {entity}Id = {DIFFERENT_SAMPLE_ID};
        final var expected{Entity}Dto = {DIFFERENT_SAMPLE_DTO_INSTANCE};
        when({dataSourceFieldName}.get{Entity}({entity}Id)).thenReturn(expected{Entity}Dto);

        // Act
        final var actual = {useCaseFieldName}.execute({entity}Id);

        // Assert
        assertThat(actual).isEqualTo(expected{Entity}Dto);
        assertThat(actual.{ID_FIELD}()).isEqualTo({DIFFERENT_EXPECTED_ID});
        assertThat(actual.{FIELD_1}()).isEqualTo({DIFFERENT_EXPECTED_VALUE_1});

        // Verify
        verify({dataSourceFieldName}).get{Entity}({entity}Id);
    }

    // ============================================================================
    // リスト取得パターンのテスト
    // ============================================================================

    @Test
    void execute_正常なuserID_{Entity}リストを返す() {
        // Arrange
        final var userId = {SAMPLE_USER_ID};
        final var expected{Entity}List = Arrays.asList(
            {SAMPLE_DTO_INSTANCE_1},
            {SAMPLE_DTO_INSTANCE_2}
        );
        when({dataSourceFieldName}.get{Entities}(userId)).thenReturn(expected{Entity}List);

        // Act
        final var actual = {useCaseFieldName}.execute(userId);

        // Assert
        assertThat(actual).hasSize(2);
        assertThat(actual).containsExactlyElementsOf(expected{Entity}List);
        assertThat(actual.get(0).{ID_FIELD}()).isEqualTo({EXPECTED_FIRST_ID});
        assertThat(actual.get(1).{ID_FIELD}()).isEqualTo({EXPECTED_SECOND_ID});

        // Verify
        verify({dataSourceFieldName}).get{Entities}(userId);
    }

    @Test
    void execute_存在しないuserID_空のリストを返す() {
        // Arrange
        final var userId = {NON_EXISTENT_USER_ID};
        final var empty{Entity}List = Arrays.<{DTO_CLASS_NAME}>asList();
        when({dataSourceFieldName}.get{Entities}(userId)).thenReturn(empty{Entity}List);

        // Act
        final var actual = {useCaseFieldName}.execute(userId);

        // Assert
        assertThat(actual).isEmpty();

        // Verify
        verify({dataSourceFieldName}).get{Entities}(userId);
    }

    @Test
    void execute_userIDがnull_空のリストを返す() {
        // Arrange
        final Integer userId = null;
        final var empty{Entity}List = Arrays.<{DTO_CLASS_NAME}>asList();
        when({dataSourceFieldName}.get{Entities}(userId)).thenReturn(empty{Entity}List);

        // Act
        final var actual = {useCaseFieldName}.execute(userId);

        // Assert
        assertThat(actual).isEmpty();

        // Verify
        verify({dataSourceFieldName}).get{Entities}(userId);
    }

    // ============================================================================
    // 例外処理テスト
    // ============================================================================

    @Test
    void execute_DataSourceが例外をスロー_例外が伝播される() {
        // Arrange
        final var {entity}Id = {INVALID_ID};
        final var expectedException = new RuntimeException("{Entity} not found");
        when({dataSourceFieldName}.get{Entity}({entity}Id)).thenThrow(expectedException);

        // Act & Assert
        assertThatThrownBy(() -> {useCaseFieldName}.execute({entity}Id))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("{Entity} not found");

        // Verify
        verify({dataSourceFieldName}).get{Entity}({entity}Id);
    }

    @Test
    void execute_DataSourceがnullを返す_nullが返される() {
        // Arrange
        final var {entity}Id = {NULL_RETURN_ID};
        when({dataSourceFieldName}.get{Entity}({entity}Id)).thenReturn(null);

        // Act
        final var actual = {useCaseFieldName}.execute({entity}Id);

        // Assert
        assertThat(actual).isNull();

        // Verify
        verify({dataSourceFieldName}).get{Entity}({entity}Id);
    }

    // ============================================================================
    // 引数バリデーションテスト
    // ============================================================================

    @Test
    void execute_{ENTITY}IDがnull_DataSourceが呼び出される() {
        // Arrange
        final Integer {entity}Id = null;
        final var expected{Entity}Dto = {NULL_ID_DTO_INSTANCE};
        when({dataSourceFieldName}.get{Entity}({entity}Id)).thenReturn(expected{Entity}Dto);

        // Act
        final var actual = {useCaseFieldName}.execute({entity}Id);

        // Assert
        assertThat(actual).isEqualTo(expected{Entity}Dto);

        // Verify
        verify({dataSourceFieldName}).get{Entity}({entity}Id);
    }

    @Test
    void execute_負の{ENTITY}ID_DataSourceが呼び出される() {
        // Arrange
        final var {entity}Id = {NEGATIVE_ID};
        final var expected{Entity}Dto = {NEGATIVE_ID_DTO_INSTANCE};
        when({dataSourceFieldName}.get{Entity}({entity}Id)).thenReturn(expected{Entity}Dto);

        // Act
        final var actual = {useCaseFieldName}.execute({entity}Id);

        // Assert
        assertThat(actual).isEqualTo(expected{Entity}Dto);

        // Verify
        verify({dataSourceFieldName}).get{Entity}({entity}Id);
    }

    // ============================================================================
    // モック検証テスト
    // ============================================================================

    @Test
    void execute_DataSourceが正しく呼び出される_モックが検証される() {
        // Arrange
        final var {entity}Id = {MOCK_VERIFICATION_ID};
        final var {entity}Dto = {MOCK_DTO_INSTANCE};
        when({dataSourceFieldName}.get{Entity}(any({ID_TYPE}.class))).thenReturn({entity}Dto);

        // Act
        {useCaseFieldName}.execute({entity}Id);

        // Assert
        verify({dataSourceFieldName}).get{Entity}({entity}Id);
    }

    // ============================================================================
    // ビジネスロジックテスト（必要に応じて追加）
    // ============================================================================

    @Test
    void execute_削除フラグがtrue_結果に含まれない() {
        // ※このテストはビジネスロジックがUseCaseに含まれる場合のサンプル
        // 実際のロジックに応じてテストケースを追加・修正してください

        // Arrange
        final var userId = {SAMPLE_USER_ID};
        final var {entity}ListWithDeleted = Arrays.asList(
            {ACTIVE_DTO_INSTANCE},      // isDeleted = false
            {DELETED_DTO_INSTANCE}      // isDeleted = true
        );
        when({dataSourceFieldName}.get{Entities}(userId)).thenReturn({entity}ListWithDeleted);

        // Act
        final var actual = {useCaseFieldName}.execute(userId);

        // Assert - 削除フラグがfalseのもののみ返されることを検証
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0).isDeleted()).isFalse();

        // Verify
        verify({dataSourceFieldName}).get{Entities}(userId);
    }
}

// ============================================================================
// テンプレート置換変数説明
// ============================================================================
/*
{USE_CASE_CLASS_NAME}       - テスト対象のUseCaseクラス名 (例: GetTaskUseCase)
{DATA_SOURCE_INTERFACE}     - 依存するDataSourceインターフェース名 (例: TasksQueryDataSource)
{DTO_CLASS_NAME}            - DTOクラス名 (例: TaskDto, GoodDto)
{dataSourceFieldName}       - DataSourceのフィールド名 (例: tasksQueryDataSource)
{useCaseFieldName}          - UseCaseのフィールド名 (例: getTaskUseCase)
{Entity}                    - エンティティ名（単数形、大文字開始） (例: Task, Good)
{Entities}                  - エンティティ名（複数形、大文字開始） (例: Tasks, Goods)
{ENTITY}                    - エンティティ名（大文字） (例: TASK, GOOD)
{entity}                    - エンティティ名（小文字） (例: task, good)
{ID_TYPE}                   - IDの型 (例: Integer, Long, String)
{ID_FIELD}                  - DTOのIDフィールドアクセサメソッド名 (例: taskId(), goodId())
{FIELD_1}, {FIELD_2}        - その他の検証対象フィールドアクセサメソッド名
{BUSINESS_LOGIC_DESCRIPTION} - ビジネスロジックの説明

サンプル値変数:
{SAMPLE_ID}                 - テスト用のサンプルID (例: 1)
{SAMPLE_DTO_INSTANCE}       - テスト用のDTOインスタンス
{EXPECTED_ID}               - 期待されるID値
{EXPECTED_VALUE_1}          - 期待される値1
{SAMPLE_USER_ID}            - テスト用のユーザーID
{NON_EXISTENT_USER_ID}      - 存在しないユーザーID
{INVALID_ID}                - 無効なID
{NULL_RETURN_ID}            - null返却用ID
{NEGATIVE_ID}               - 負のID値
{MOCK_VERIFICATION_ID}      - モック検証用ID
{ACTIVE_DTO_INSTANCE}       - アクティブなDTOインスタンス
{DELETED_DTO_INSTANCE}      - 削除済みDTOインスタンス
等...
*/