package communicationLayer.oscar;

import com.aol.acc.AccPreferencesHook;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AccPreferencesHookRoboAdminImpl extends AccPreferencesHook
{
	Map<String, String> map = new HashMap<String, String>();

	public String getValue(String specifier)
	{
		return map.get(specifier);
	}

	public String getDefaultValue(String specifier)
	{
		return null;
	}

	public void setValue(String specifier, String value)
	{
		map.put(specifier, value);
	}

	public void reset(String specifier)
	{
		map.put(specifier, null);
	}

	public String[] getChildSpecifiers(String specifier)
	{
		List<String> v = new ArrayList<String>();
		for (String s : map.keySet())
			if (s.startsWith(specifier) && !s.equals(specifier))
				v.add(s);
		if (v.size() > 0)
			return v.toArray(new String[1]);
		else
			return null;
	}
}
