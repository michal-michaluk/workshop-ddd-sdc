package pl.sdc.operations.moneytransfer;

import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AvailableClearingChoices {

    private final List<ClearingChoices> available;

    public static AvailableClearingChoices noClearingRequired() {
        return new AvailableClearingChoices(Collections.emptyList());
    }

    public List<ClearingChoices> visible() {
        // if there is not clearing required or there is only one option of clearing
        // then customer doesn't have available Clearing Choice
        // and proper one (or none) will be used implicitly
        if (available.size() <= 1) {
            return Collections.emptyList();
        } else {
            return available;
        }
    }

    public Optional<ClearingChoices> implicitly() {
        // if there is not clearing required or there is only one option of clearing
        // and proper one (or none) will be used implicitly
        if (available.size() == 1) {
            return Optional.of(available.get(0));
        }
        return Optional.empty();
    }

    public Optional<ClearingChoices> suggested() {
        // if there is more then one available Clearing Choices
        // then one with highest priority is suggested
        if (available.size() > 1) {
            return Optional.of(available.get(0));
        }
        return Optional.empty();
    }

}
