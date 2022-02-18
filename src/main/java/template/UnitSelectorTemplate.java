/* 由程序自动生成，请勿修改。*/
package template;

public record UnitSelectorTemplate(int id, String name, int unit_limit, String sort_by, int use_circle_area,
                                   String center, float range_min, float range_max, float angle_min, float angle_max,
                                   int use_rect_area, float max_x, float min_x, float max_y, float min_y,
                                   String condition, int[] unit_id, int[] unit_type, int[] not_in_unit_type,
                                   int[] ai_type, int[] unit_team) {

    public static UnitSelectorTemplate parse(CustomByteBuffer cbb) {
        var id = cbb.getInt();
        var name = cbb.getString();
        var unit_limit = cbb.getInt();
        var sort_by = cbb.getString();
        var use_circle_area = cbb.getInt();
        var center = cbb.getString();
        var range_min = cbb.getFloat();
        var range_max = cbb.getFloat();
        var angle_min = cbb.getFloat();
        var angle_max = cbb.getFloat();
        var use_rect_area = cbb.getInt();
        var max_x = cbb.getFloat();
        var min_x = cbb.getFloat();
        var max_y = cbb.getFloat();
        var min_y = cbb.getFloat();
        var condition = cbb.getString();
        var unit_idLength = cbb.getInt();
        var unit_id = new int[unit_idLength];
        for (int i = 0; i < unit_idLength; i++){
            unit_id[i] = cbb.getInt();
        }
        var unit_typeLength = cbb.getInt();
        var unit_type = new int[unit_typeLength];
        for (int i = 0; i < unit_typeLength; i++) {
            unit_type[i] = cbb.getInt();
        }
        var not_in_unit_typeLength = cbb.getInt();
        var not_in_unit_type = new int[not_in_unit_typeLength];
        for (int i = 0; i < not_in_unit_typeLength; i++) {
            not_in_unit_type[i] = cbb.getInt();
        }
        var ai_typeLength = cbb.getInt();
        var ai_type = new int[ai_typeLength];
        for (int i = 0; i < ai_typeLength; i++) {
            ai_type[i] = cbb.getInt();
        }
        var unit_teamLength = cbb.getInt();
        var unit_team = new int[unit_teamLength];
        for (int i = 0; i < unit_teamLength; i++) {
            unit_team[i] = cbb.getInt();
        }

        var temp = new UnitSelectorTemplate(id, name, unit_limit, sort_by, use_circle_area, center, range_min, range_max, angle_min, angle_max, use_rect_area, max_x, min_x, max_y, min_y, condition, unit_id, unit_type, not_in_unit_type, ai_type, unit_team);
        return temp;
    }
}
