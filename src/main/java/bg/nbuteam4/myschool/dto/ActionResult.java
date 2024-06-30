package bg.nbuteam4.myschool.dto;

import bg.nbuteam4.myschool.enums.ActionResultType;

public record ActionResult(String message, ActionResultType type) {
}
